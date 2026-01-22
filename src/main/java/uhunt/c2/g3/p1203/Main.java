package uhunt.c2.g3.p1203;

import java.util.*;

public class Main {
    private static final Scanner in = new Scanner(System.in);

    public static void main(String... args) {
        List<Integer> tasks = new LinkedList<>();
        List<Integer> periods = new LinkedList<>();
        while (in.next().equals("Register")) {
            int task = in.nextInt();
            int period = in.nextInt();

            tasks.add(task);
            periods.add(period);
        }

        int[] tasksArr = listToArray(tasks);
        int[] periodsArr = listToArray(periods);
        int totalSequence = in.nextInt();

        Solution solution = new Solution(tasksArr, periodsArr, totalSequence);

        int[] sequenceOfTasks = solution.getSequenceOfTasks();
        for (int task : sequenceOfTasks) {
            System.out.println(task);
        }
    }

    private static int[] listToArray(List<Integer> list) {
        int[] array = new int[list.size()];
        Iterator<Integer> value = list.iterator();
        for (int i = 0; i < array.length; i++) {
            array[i] = value.next();
        }
        return array;
    }
}

class Solution {
    private final int[] tasks;
    private final int[] periods;
    private final int totalSequence;

    public Solution(int[] tasks, int[] periods, int totalSequence) {
        this.tasks = tasks;
        this.periods = periods;
        this.totalSequence = totalSequence;
    }

    public int[] getSequenceOfTasks() {
        // schedule, task, period
        Comparator<int[]> sortPairAsc = Comparator
                .<int[]>comparingInt(p -> p[0])
                .thenComparing(p -> p[1])
                .thenComparing(p -> p[2]);
        PriorityQueue<int[]> queue = new PriorityQueue<>(sortPairAsc);

        for (int i = 0; i < tasks.length; i++) {
            int[] taskMeta = new int[]{periods[i], tasks[i], periods[i]};
            queue.add(taskMeta);
        }

        int[] sequenceOfTasks = new int[totalSequence];
        for (int sequence = 0; sequence < totalSequence && !queue.isEmpty(); sequence++) {
            int[] taskMeta = queue.remove();
            int schedule = taskMeta[0];
            int task = taskMeta[1];
            int period = taskMeta[2];

            sequenceOfTasks[sequence] = task;

            int nextSchedule = schedule + period;
            taskMeta[0] = nextSchedule;
            queue.add(taskMeta);
        }

        return sequenceOfTasks;
    }
}
