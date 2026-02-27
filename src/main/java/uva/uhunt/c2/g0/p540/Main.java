package uva.uhunt.c2.g0.p540;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 540 - Team Queue
 * Time limit: 3.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&page=show_problem&category=0&problem=481&mosmsg=Submission+received+with+ID+30308137
 */
public class Main {
    public static void main(String... args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder out = new StringBuilder();

        int totalTeams = Integer.parseInt(in.readLine());
        for (int testCaseId = 1; totalTeams > 0; testCaseId++) {
            ArrayList<LinkedList<Integer>> teams = new ArrayList<>(totalTeams);
            for (int i = 0; i < totalTeams; i++) {
                String[] queueText = in.readLine().split(" ");
                LinkedList<Integer> queue = IntStream.range(1, queueText.length)
                        .mapToObj(idx -> Integer.parseInt(queueText[idx]))
                        .collect(Collectors.toCollection(LinkedList::new));
                teams.add(queue);
            }

            LinkedList<String[]> instructions = new LinkedList<>();
            String[] instruction = in.readLine().split(" ");
            while (!instruction[0].equals(Instruction.STOP)) {
                instructions.addLast(instruction);
                instruction = in.readLine().split(" ");
            }

            TestCase testCase = new TestCase(teams, instructions);

            Solution solution = new Solution(testCase, out);

            out.append("Scenario #").append(testCaseId).append('\n');
            solution.solve();
            out.append('\n');

            totalTeams = Integer.parseInt(in.readLine());
        }

        in.close();
        System.out.print(out);
    }
}

class Solution {
    private final TestCase testCase;
    private final StringBuilder output;

    public Solution(TestCase testCase, StringBuilder output) {
        this.testCase = testCase;
        this.output = output;
    }

    public void solve() {
        TeamQueue teamQueue = new TeamQueue();
        for (int team = 0; team < testCase.teams.size(); team++) {
            for (int element : testCase.teams.get(team)) {
                teamQueue.register(element, team);
            }
        }

        for (String[] instruction : testCase.instructions) {
            switch (instruction[0]) {
                case Instruction.ENQUEUE:
                    int enqueueElement = Integer.parseInt(instruction[1]);
                    teamQueue.enqueue(enqueueElement);
                    break;
                case Instruction.DEQUEUE:
                    int dequeueElement = teamQueue.dequeue();
                    output.append(dequeueElement).append('\n');
                    break;
                case Instruction.STOP:
                    break;
            }
        }
    }
}

class TeamQueue {
    private final Map<Integer, Integer> teamPerElement = new HashMap<>();
    private final LinkedHashMap<Integer, LinkedList<Integer>> elementsPerTeam = new LinkedHashMap<>();

    public TeamQueue() {
    }

    public void register(int element, int team) {
        teamPerElement.put(element, team);
    }

    public void enqueue(int element) {
        int lastTeam = teamPerElement.size() + elementsPerTeam.size();
        int team = teamPerElement.getOrDefault(element, lastTeam);
        elementsPerTeam.computeIfAbsent(team, (k) -> new LinkedList<>()).addLast(element);
    }

    public int dequeue() {
        for (int team : elementsPerTeam.keySet()) {
            int element = elementsPerTeam.get(team).removeFirst();
            if (elementsPerTeam.get(team).isEmpty()) elementsPerTeam.remove(team);
            return element;
        }
        return 0;
    }
}

class TestCase {
    public final ArrayList<LinkedList<Integer>> teams;
    public final LinkedList<String[]> instructions;

    public TestCase(ArrayList<LinkedList<Integer>> teams, LinkedList<String[]> instructions) {
        this.teams = teams;
        this.instructions = instructions;
    }
}

class Instruction {
    public static final String ENQUEUE = "ENQUEUE";
    public static final String DEQUEUE = "DEQUEUE";
    public static final String STOP = "STOP";
}
