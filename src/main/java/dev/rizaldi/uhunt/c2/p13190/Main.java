package dev.rizaldi.uhunt.c2.p13190;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * 13190 - Rockabye Tobby
 * Time limit: 1.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=5101
 */
public class Main {
    public static void main(String... args) {
        Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 16));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 16));

        int totalCase = in.nextInt();
        for (int i = 0; i < totalCase; i++) {
            int totalMedicine = in.nextInt();
            int totalConsumption = in.nextInt();

            Medicine[] medicines = new Medicine[totalMedicine];
            for (int j = 0; j < totalMedicine; j++) {
                String name = in.next();
                int frequency = in.nextInt();
                medicines[j] = new Medicine(name, frequency, j);
            }

            Solution solution = new Solution(totalMedicine, totalConsumption, medicines);
            MedicineEvent[] events = solution.getEvents();

            for (MedicineEvent event : events) {
                out.print(event.consumedAt);
                out.print(' ');
                out.println(event.medicine.name);
            }
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Medicine {
    public final String name;
    public final int frequency;
    public final int priority;

    public Medicine(String name, int frequency, int priority) {
        this.name = name;
        this.frequency = frequency;
        this.priority = priority;
    }
}

final class MedicineEvent implements Comparable<MedicineEvent> {
    public final Medicine medicine;
    public final int consumedAt;

    public MedicineEvent(Medicine medicine) {
        this(medicine, medicine.frequency);
    }

    public MedicineEvent(Medicine medicine, int consumedAt) {
        this.medicine = medicine;
        this.consumedAt = consumedAt;
    }

    public MedicineEvent nextEvent() {
        return new MedicineEvent(medicine, consumedAt + medicine.frequency);
    }

    @Override
    public int compareTo(MedicineEvent o) {
        int c1 = Integer.compare(consumedAt, o.consumedAt);
        int c2 = Integer.compare(medicine.priority, o.medicine.priority);
        return c1 != 0 ? c1 : c2;
    }
}

class Solution {
    private final int totalMedicine;
    private final int totalConsumption;
    private final Medicine[] medicines;

    public Solution(int totalMedicine, int totalConsumption, Medicine[] medicines) {
        this.totalMedicine = totalMedicine;
        this.totalConsumption = totalConsumption;
        this.medicines = medicines;
    }

    public MedicineEvent[] getEvents() {
        MedicineEvent[] events = new MedicineEvent[totalConsumption];

        PriorityQueue<MedicineEvent> eventq = new PriorityQueue<>();
        for (Medicine m : medicines) eventq.add(new MedicineEvent(m));

        for (int i = 0; i < totalConsumption && !eventq.isEmpty(); i++) {
            MedicineEvent currentSchedule = eventq.poll();
            events[i] = currentSchedule;
            eventq.add(currentSchedule.nextEvent());
        }

        return events;
    }
}
