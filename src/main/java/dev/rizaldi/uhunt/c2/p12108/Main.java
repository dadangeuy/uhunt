package dev.rizaldi.uhunt.c2.p12108;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Iterator;

public class Main {
    private static BufferedReader in;
    private static StringBuilder out;

    public static void main(String... args) {
        in = new BufferedReader(new InputStreamReader(System.in));
        out = new StringBuilder();

        Iterator<Integer> nextInts = in.lines()
            .flatMap(l -> Arrays.stream(l.split(" ")))
            .filter(l -> !l.isEmpty())
            .mapToInt(Integer::parseInt)
            .iterator();

        int caseId = 1;
        while (true) {
            int totalStudent = nextInts.next();
            if (totalStudent == 0) break;

            int[][] students = new int[totalStudent][3];
            for (int i = 0; i < totalStudent; i++) {
                int awaken = nextInts.next();
                int asleep = nextInts.next();
                int state = nextInts.next();

                students[i][0] = awaken;
                students[i][1] = asleep;
                students[i][2] = state;
            }

            Solution solution = new Solution(totalStudent, students);
            int time = solution.findFirstTimeAllStudentAwaken();

            out.append("Case ").append(caseId++).append(": ").append(time).append('\n');
        }

        System.out.print(out);
    }
}

/**
 * 1. brute force, iterate from time 1..INF and update student state
 * 2. if >= half student are awake, student can't sleep
 * 3. to avoid infinite cycle, store the state of each student in a map
 * 4. state to store: actionID of each student, action: awake/sleep, actionID: index in action sequence, action sequence: awake[0]-awake[1]-sleep[2]-sleep[3]
 * <p>
 * Time complexity: O((awake+asleep)^student) -> O(10^10), might be TLE
 */
class Solution {
    private static final int AWAKEN = 1;
    private static final int ASLEEP = 2;
    private final int totalStudent;
    private final int[][] students;

    public Solution(int totalStudent, int[][] students) {
        this.totalStudent = totalStudent;
        this.students = students;
    }

    public int findFirstTimeAllStudentAwaken() {
        int[][] actionsPerStudent = new int[totalStudent][];
        for (int i = 0; i < totalStudent; i++) {
            int awaken = students[i][0];
            int asleep = students[i][1];

            int[] actions = new int[awaken + asleep];
            for (int j = 0; j < awaken; j++) actions[j] = AWAKEN;
            for (int j = awaken; j < awaken + asleep; j++) actions[j] = ASLEEP;

            actionsPerStudent[i] = actions;
        }

        int[] actionIdPerStudent = new int[totalStudent];
        for (int i = 0; i < totalStudent; i++) {
            int state = students[i][2];
            actionIdPerStudent[i] = state - 1;
        }

        long timeLimit = 1;
        for (int i = 0; i < totalStudent; i++) {
            int awaken = students[i][0];
            int asleep = students[i][1];
            timeLimit *= (awaken + asleep);
        }

        for (int time = 1; time <= timeLimit; time++) {
            // count awaken
            int awakenCount = 0;
            int asleepCount = 0;
            for (int student = 0; student < totalStudent; student++) {
                int[] actions = actionsPerStudent[student];
                int actionId = actionIdPerStudent[student];
                int action = actions[actionId];

                if (action == AWAKEN) awakenCount++;
                else asleepCount++;
            }

            boolean allAwaken = awakenCount == totalStudent;
            if (allAwaken) return time;

            boolean halfAwaken = awakenCount >= asleepCount;
            for (int student = 0; student < totalStudent; student++) {
                int[] actions = actionsPerStudent[student];
                int actionId = actionIdPerStudent[student];
                int action = actions[actionId];

                int nextActionId = (actionId + 1) % actions.length;
                if (action == AWAKEN && halfAwaken) nextActionId = 0;
                actionIdPerStudent[student] = nextActionId;
            }
        }

        return -1;
    }
}
