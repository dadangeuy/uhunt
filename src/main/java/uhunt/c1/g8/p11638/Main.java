package uhunt.c1.g8.p11638;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * 11638 - Temperature Monitoring
 * Time limit: 1.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&page=show_problem&problem=2685
 */
public class Main {
    public static void main(String... args) {
        Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 16));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 16));

        int totalCase = in.nextInt();
        for (int caseId = 1; caseId <= totalCase; caseId++) {
            int interval = in.nextInt();
            int delay = in.nextInt();

            int[] thresholds = new int[4];
            thresholds[0] = in.nextInt();
            thresholds[1] = in.nextInt();
            thresholds[2] = in.nextInt();
            thresholds[3] = in.nextInt();

            int config = in.nextInt();

            int totalMeasurement = in.nextInt();
            int[][] measurements = new int[totalMeasurement][];
            for (int i = 0; i < totalMeasurement; i++) {
                int duration = in.nextInt();
                int temperature = in.nextInt();
                measurements[i] = new int[]{duration, temperature};
            }

            Solution solution = new Solution(interval, delay, thresholds, config, totalMeasurement, measurements);
            boolean[] statuses = solution.getTriggerStatuses();

            out.format(
                "Case %d: %s %s %s %s\n",
                caseId, humanize(statuses[0]), humanize(statuses[1]), humanize(statuses[2]), humanize(statuses[3])
            );
        }

        in.close();
        out.flush();
        out.close();
    }

    private static String humanize(boolean value) {
        return value ? "yes" : "no";
    }
}

class Solution {
    private final int interval;
    private final int delay;
    private final int[] thresholds;
    private final int config;
    private final int totalMeasurement;
    private final int[][] measurements;

    public Solution(int interval, int delay, int[] thresholds, int config, int totalMeasurement, int[][] measurements) {
        this.interval = interval;
        this.delay = delay;
        this.thresholds = thresholds;
        this.config = config;
        this.totalMeasurement = totalMeasurement;
        this.measurements = measurements;
    }

    public boolean[] getTriggerStatuses() {
        boolean[] triggerStatuses = new boolean[]{false, false, false, false};

        int startTime = 0;
        for (int[] measurement : measurements) {
            int duration = measurement[0], temperature = measurement[1];

            // from-exclusive, until-inclusive
            int from = startTime, until = startTime + duration;
            startTime = until;

            int nextInterval = from <= delay ? delay : from + (interval - ((from - delay) % interval));
            if (nextInterval > until) continue;

            for (int alarmId = 0; alarmId < 4; alarmId++) {
                if (disabled(alarmId)) continue;
                if (triggerStatuses[alarmId]) continue;

                boolean lowTriggered = low(alarmId) && temperature < thresholds[alarmId];
                boolean highTriggered = high(alarmId) && temperature > thresholds[alarmId];
                boolean triggered = lowTriggered || highTriggered;

                if (triggered) triggerStatuses[alarmId] = true;
            }
        }

        return triggerStatuses;
    }

    private boolean enabled(int alarm) {
        return !disabled(alarm);
    }

    private boolean disabled(int alarm) {
        return ((config >> alarm) & 1) == 0;
    }

    private boolean high(int alarm) {
        return !low(alarm);
    }

    private boolean low(int alarm) {
        return ((config >> (alarm + 4)) & 1) == 0;
    }
}
