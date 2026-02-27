package uva.uhunt.c1.g6.p12136;

import java.io.*;
import java.util.Scanner;

public class Main {
    public static void main(String... args) {
        Scanner in = new Scanner(new BufferedInputStream(System.in, 2 << 16));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 2 << 16));

        int totalCase = in.nextInt();
        for (int i = 0; i < totalCase; i++) {
            String wifeStartedAt = in.next();
            String wifeEndedAt = in.next();
            String meetingStartedAt = in.next();
            String meetingEndedAt = in.next();

            Solution solution = new Solution(wifeStartedAt, wifeEndedAt, meetingStartedAt, meetingEndedAt);
            boolean possible = solution.isAbleToAttendMeeting();

            out.format("Case %d: ", i + 1);
            if (possible) out.println("Hits Meeting");
            else out.println("Mrs Meeting");
        }

        in.close();
        out.flush();
        out.close();
    }
}

/**
 * 1) convert the time (hh:mm) into integer (hh*100 + mm)
 * 2) check if it's overlapping:
 *    '--|--'-|
 *    '--|----|--'
 *    |--'-'---|
 *    |--'-|--'
 *    ' = wife
 *    | = meeting
 * 3) if it's not overlapping, then danny can attend meeting
 */
class Solution {
    private final String wifeStartedAt;
    private final String wifeEndedAt;
    private final String meetingStartedAt;
    private final String meetingEndedAt;

    public Solution(String wifeStartedAt, String wifeEndedAt, String meetingStartedAt, String meetingEndedAt) {
        this.wifeStartedAt = wifeStartedAt;
        this.wifeEndedAt = wifeEndedAt;
        this.meetingStartedAt = meetingStartedAt;
        this.meetingEndedAt = meetingEndedAt;
    }

    public boolean isAbleToAttendMeeting() {
        int wifeStart = toInt(wifeStartedAt);
        int wifeEnd = toInt(wifeEndedAt);
        int meetStart = toInt(meetingStartedAt);
        int meetEnd = toInt(meetingEndedAt);

        if (wifeEnd < wifeStart) wifeEnd += 2400;
        if (meetEnd < meetStart) meetEnd += 2400;

        boolean overlap = between(wifeStart, meetStart, wifeEnd) || between(meetStart, wifeStart, meetEnd);

        return !overlap;
    }

    private int toInt(String time) {
        int hour = Integer.parseInt(time.substring(0, 2));
        int minute = Integer.parseInt(time.substring(3, 5));
        return hour * 100 + minute;
    }

    private boolean between(int from, int value, int to) {
        return from <= value && value <= to;
    }
}
