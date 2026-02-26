package uva.c4.g2.p452;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 * 452 - Project Scheduling
 * Time limit: 3.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&page=show_problem&problem=393
 */
public class Main {
    public static void main(final String... args) throws IOException {
        final BufferedReader in = new BufferedReader(new InputStreamReader(System.in), 1 << 16);
        final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out), 1 << 16);
        final Process process = new Process();

        final int totalTestCases = Integer.parseInt(in.readLine());
        for (int i = 0; i < totalTestCases; i++) {
            final List<Task> tasks = new ArrayList<>();
            for (int j = 0; ; j++) {
                String line = in.readLine();
                while (j == 0 && line != null && line.isEmpty()) line = in.readLine();
                if (line == null || line.isEmpty()) break;

                final String[] split = line.split(" ");
                final char taskId = split[0].charAt(0);
                final int totalDays = Integer.parseInt(split[1]);
                final char[] previousTaskIds = split.length < 3 ? new char[0] : split[2].toCharArray();

                final Task task = new Task(taskId, totalDays, previousTaskIds);
                tasks.add(task);
            }

            if (tasks.isEmpty()) break;

            final Input input = new Input(tasks);
            final Output output = process.process(input);

            if (i > 0) out.write('\n');
            out.write(String.format("%d\n", output.totalDaysToCompleteTasks));
        }


        in.close();
        out.flush();
        out.close();
    }
}

class Task {
    public final char id;
    public final int totalDays;
    public final char[] previousTaskIds;

    public Task(final char id, final int totalDays, final char[] previousTaskIds) {
        this.id = id;
        this.totalDays = totalDays;
        this.previousTaskIds = previousTaskIds;
    }
}

class Input {
    public final List<Task> tasks;

    public Input(final List<Task> tasks) {
        this.tasks = tasks;
    }
}

class Output {
    public final int totalDaysToCompleteTasks;

    public Output(final int totalDaysToCompleteTasks) {
        this.totalDaysToCompleteTasks = totalDaysToCompleteTasks;
    }
}

class Process {
    /**
     * idea(s):
     * - use topological sort to get the sequence of task that doesn't break the dependencies
     * - task's start time equals to the end time of its previous tasks
     * - task's end time equals to start time + processing time (total days)
     */
    public Output process(final Input input) {
        // index task by id
        final Map<Character, Task> taskPerId = new HashMap<>();
        for (final Task task : input.tasks) {
            taskPerId.put(task.id, task);
        }

        // build graph network from task dependencies
        final Graph<Character> graph = new Graph<>();
        for (final Task task : input.tasks) {
            final char id = task.id;
            for (final char previousId : task.previousTaskIds) {
                graph.add(previousId, id);
            }
        }

        // lock each task based on the number of previous task that needs to be completed
        final LockManager<Character> lockManager = new LockManager<>();
        for (final Task task : input.tasks) {
            final char id = task.id;
            lockManager.lock(id, task.previousTaskIds.length);
        }

        // traverse graph based on topological sort algorithm
        final Queue<Character> availableTasks = new LinkedList<>();
        final LinkedHashSet<Character> processedTasks = new LinkedHashSet<>();

        for (final Task task : input.tasks) {
            if (lockManager.isUnlocked(task.id)) {
                availableTasks.add(task.id);
                processedTasks.add(task.id);
            }
        }
        while (!availableTasks.isEmpty()) {
            final char id = availableTasks.remove();

            // release next tasks
            for (final char nextId : graph.get(id)) {
                lockManager.unlock(nextId);
            }

            // queue next tasks
            for (final char nextId : graph.get(id)) {
                if (processedTasks.contains(nextId)) continue;
                if (lockManager.isLocked(nextId)) continue;

                availableTasks.add(nextId);
                processedTasks.add(nextId);
            }
        }

        // calculate start and finish time of each task
        final Map<Character, Integer> startedAtPerId = new HashMap<>();
        final Map<Character, Integer> finishedAtPerId = new HashMap<>();
        for (final char id : processedTasks) {
            final Task task = taskPerId.get(id);

            int startedAt = 0;
            for (final char previousId : task.previousTaskIds) {
                final int previousFinishedAt = finishedAtPerId.getOrDefault(previousId, 0);
                startedAt = Math.max(startedAt, previousFinishedAt);
            }
            startedAtPerId.put(id, startedAt);

            int finishedAt = startedAt + task.totalDays;
            finishedAtPerId.put(id, finishedAt);
        }

        final int maxFinishedAt = finishedAtPerId.values().stream().mapToInt(i -> i).max().orElse(0);
        return new Output(maxFinishedAt);
    }
}

class LockManager<V> {
    private final Map<V, Integer> locks;

    public LockManager() {
        this.locks = new HashMap<>();
    }

    public void lock(V key, int times) {
        locks.compute(key, (k, v) -> v == null ? times : v + times);
    }

    public void lock(V key) {
        lock(key, 1);
    }

    public void unlock(V key) {
        locks.compute(key, (k, v) -> v == null ? 0 : Math.max(v - 1, 0));
    }

    public boolean isLocked(V key) {
        return locks.getOrDefault(key, 0) > 0;
    }

    public boolean isUnlocked(V key) {
        return locks.getOrDefault(key, 0) == 0;
    }
}

class Graph<V> {
    private final Map<V, Set<V>> edges;

    public Graph() {
        this.edges = new HashMap<>();
    }

    public void add(V from, V into) {
        edges.computeIfAbsent(from, k -> new HashSet<>()).add(into);
    }

    public Set<V> get(V from) {
        return edges.getOrDefault(from, Collections.emptySet());
    }

    public void remove(V from, V into) {
        get(from).remove(into);
    }
}
