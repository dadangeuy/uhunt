package uva.uhunt.c1.g8.p12148;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    public static void main(String... args) {
        Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 16));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 16));

        while (in.hasNextInt()) {
            int totalMeasure = in.nextInt();
            if (totalMeasure == 0) break;

            Record[] records = new Record[totalMeasure];
            for (int i = 0; i < totalMeasure; i++) {
                int day = in.nextInt();
                int month = in.nextInt();
                int year = in.nextInt();
                int consumption = in.nextInt();

                LocalDate date = LocalDate.of(year, month, day);
                Record record = new Record(date, consumption);
                records[i] = record;
            }

            Solution solution = new Solution(records);
            int[] totalDayAndConsumption = solution.getTotalDayAndConsumption();
            int totalDay = totalDayAndConsumption[0];
            int totalConsumption = totalDayAndConsumption[1];

            out.format("%d %d\n", totalDay, totalConsumption);
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Record {
    public final LocalDate date;
    public final int consumption;

    public Record(LocalDate date, int consumption) {
        this.date = date;
        this.consumption = consumption;
    }
}

class Solution {
    // sorted
    private final Record[] records;

    public Solution(Record[] records) {
        this.records = records;
    }

    public int[] getTotalDayAndConsumption() {
        int totalDay = 0;
        int totalConsumption = 0;

        for (int i = 0; i < records.length - 1; i++) {
            Record prev = records[i], next = records[i + 1];

            boolean consecutive = prev.date.plusDays(1).isEqual(next.date);
            if (!consecutive) continue;

            totalDay++;
            totalConsumption += next.consumption - prev.consumption;
        }

        return new int[]{totalDay, totalConsumption};
    }
}
