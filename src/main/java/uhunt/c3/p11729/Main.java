package uhunt.c3.p11729;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class Main {
    public static void main(String... args) {
        Scanner in = new Scanner(new BufferedInputStream(System.in, 2 << 16));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 2 << 16));

        int caseNum = 0;
        while (true) {
            int totalSoldier = in.nextInt();
            if (totalSoldier == 0) break;

            int[][] tasks = new int[totalSoldier][2];
            for (int i = 0; i < totalSoldier; i++) {
                tasks[i][0] = in.nextInt();
                tasks[i][1] = in.nextInt();
            }

            Solution solution = new Solution(totalSoldier, tasks);
            int time = solution.getTotalTimeToCompleteAllTasks();

            out.format("Case %d: %d\n", ++caseNum, time);
        }

        in.close();
        out.flush();
        out.close();
    }
}

/**
 * 1) prioritize task that require a long activity time to finish
 * 2) if equal, prioritize task that has short briefing time
 * 3) time complexity: O(n * log(n)), n = totalSoldier
 */
class Solution {
    private static final Comparator<int[]> compareByActivityAndBriefing = Comparator
        .<int[]>comparingInt(t -> -t[1])
        .thenComparingInt(t -> t[0]);
    private final int totalSoldier;
    private final int[][] tasks;

    public Solution(int totalSoldier, int[][] tasks) {
        this.totalSoldier = totalSoldier;
        this.tasks = tasks;
    }

    public int getTotalTimeToCompleteAllTasks() {
        Arrays.sort(tasks, compareByActivityAndBriefing);

        int completeTime = 0;
        int currentTime = 0;
        for (int[] task : tasks) {
            int briefingTime = task[0];
            int activityTime = task[1];

            currentTime += briefingTime;
            int soldierCompleteTime = currentTime + activityTime;
            completeTime = Math.max(completeTime, soldierCompleteTime);
        }

        return completeTime;
    }
}
