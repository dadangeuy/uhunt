package uhunt.c2.g8.p10138;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Main {
    private static final DateFormat format = new SimpleDateFormat("MM:dd:HH:mm");

    public static void main(String... args) throws ParseException {
        Scanner in = new Scanner(System.in);
        int totalTest = in.nextInt();

        for (int test = 0; test < totalTest; test++) {
            int[] rates = new int[24];
            for (int hour = 0; hour < 24; hour++) {
                rates[hour] = in.nextInt();
            }
            in.nextLine();

            List<Record> records = new LinkedList<>();
            String recordText;
            while (in.hasNextLine() && (recordText = in.nextLine()).length() > 0) {
                String[] splitRecordText = recordText.split(" ");
                Record record = new Record(
                        splitRecordText[0],
                        format.parse(splitRecordText[1]),
                        splitRecordText[2].equals("enter"),
                        Integer.parseInt(splitRecordText[3])
                );
                records.add(record);
            }
            records.sort(Comparator.comparing(c -> c.recordedAt));

            Solution solution = new Solution(rates, records);
            TreeMap<String, Integer> bills = solution.getBills();
            for (Map.Entry<String, Integer> entry : bills.entrySet()) {
                String license = entry.getKey();
                Integer bill = entry.getValue();
                System.out.format("%s $%d.%02d\n", license, bill / 100, bill % 100);
            }

            if (test < totalTest - 1) System.out.println();
        }
    }
}

class Record {
    public String licensePlate;
    public Date recordedAt;
    public boolean entrance;
    public int locatedAt;

    public Record(String licensePlate, Date recordedAt, boolean entrance, int locatedAt) {
        this.licensePlate = licensePlate;
        this.recordedAt = recordedAt;
        this.entrance = entrance;
        this.locatedAt = locatedAt;
    }
}

class Solution {
    private final int[] rates;
    private final List<Record> records;

    public Solution(int[] rates, List<Record> records) {
        this.rates = rates;
        this.records = records;
    }

    public TreeMap<String, Integer> getBills() {
        Map<String, List<Record>> recordsPerLicensePlate = groupRecordsByLicensePlate();

        TreeMap<String, Integer> bills = new TreeMap<>();
        for (Map.Entry<String, List<Record>> entry : recordsPerLicensePlate.entrySet()) {
            String license = entry.getKey();
            List<Record> records = entry.getValue();
            int bill = calculateSingleBill(records);
            if (bill > 0) bills.put(license, bill);
        }

        return bills;
    }

    private Map<String, List<Record>> groupRecordsByLicensePlate() {
        Map<String, List<Record>> recordsPerLicensePlate = new TreeMap<>();
        for (Record record : records) {
            recordsPerLicensePlate
                    .computeIfAbsent(record.licensePlate, k -> new LinkedList<>())
                    .add(record);
        }
        return recordsPerLicensePlate;
    }

    private Integer calculateSingleBill(List<Record> records) {
        int totalBill = 0;
        Iterator<Record> currIt = records.iterator();
        Iterator<Record> nextIt = records.iterator();
        nextIt.next();

        while (nextIt.hasNext()) {
            Record curr = currIt.next();
            Record next = nextIt.next();

            if (curr.entrance && !next.entrance) {
                int rate = rates[curr.recordedAt.getHours()];
                int distance = Math.abs(curr.locatedAt - next.locatedAt);
                int bill = rate * distance + 100;
                totalBill += bill;
            }
        }

        if (totalBill > 0) totalBill += 200;

        return totalBill;
    }
}
