package uva.c1.g2.p12822;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * 12822 - Extraordinarily large LED
 * Time limit: 1.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=4687
 */
public class Main {
    private static final DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss");

    public static void main(String... args) {
        Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 16));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 16));

        for (int caseNum = 1; in.hasNext(); caseNum++) {
            List<Record> records = new LinkedList<>();

            while (true) {
                String action = in.next();
                LocalTime time = LocalTime.parse(in.next(), timeFormat);

                if (action.equals("START")) {
                    Record record = new Record(action, time);
                    records.add(record);
                } else if (action.equals("SCORE")) {
                    String team = in.next();
                    int score = in.nextInt();
                    Record record = new Record(action, time, team, score);
                    records.add(record);
                } else if (action.equals("END")) {
                    Record record = new Record(action, time);
                    records.add(record);
                    break;
                }
            }

            Solution solution = new Solution(records);
            int totalConsumption = solution.getTotalConsumption();

            out.format("Case %d: %d\n", caseNum, totalConsumption);
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Record {
    public final String action;
    public final LocalTime time;
    public final String team;
    public final int score;

    public Record(String action, LocalTime time) {
        this.action = action;
        this.time = time;
        this.team = "";
        this.score = 0;
    }

    public Record(String action, LocalTime time, String team, int score) {
        this.action = action;
        this.time = time;
        this.team = team;
        this.score = score;
    }
}

class Solution {
    private static final int[] consumptionPerDigit = new int[]{6, 2, 5, 5, 4, 5, 6, 3, 7, 6};

    private final Record[] records;

    public Solution(List<Record> records) {
        this.records = records.toArray(new Record[0]);
    }

    public int getTotalConsumption() {
        int totalConsumption = 0;
        int homeScore = 0;
        int guestScore = 0;

        for (int i = 1; i < records.length; i++) {
            Record prev = records[i - 1];
            Record next = records[i];

            int durationSec = (int) prev.time.until(next.time, ChronoUnit.SECONDS);
            int homeConsumptionPerSec = getNumberConsumption(homeScore);
            int guestConsumptionPerSec = getNumberConsumption(guestScore);
            int consumption = homeConsumptionPerSec * durationSec + guestConsumptionPerSec * durationSec;
            totalConsumption += consumption;

            if (next.team.equals("home")) homeScore += next.score;
            if (next.team.equals("guest")) guestScore += next.score;
        }

        return totalConsumption;
    }

    private int getNumberConsumption(int number) {
        if (number == 0) return consumptionPerDigit[0];

        int sum = 0;
        for (; number > 0; number /= 10) {
            sum += consumptionPerDigit[number % 10];
        }
        return sum;
    }
}
