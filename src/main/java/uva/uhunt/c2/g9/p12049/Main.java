package uva.uhunt.c2.g9.p12049;

import uva.common.helper.Reader;

import java.util.HashMap;

public class Main {
    private static final HashMap<Integer, Integer> counts = new HashMap<>(40_000, 1);

    public static void main(String... args) throws Exception {
        Reader in = new Reader(System.in);
        int totalTest = in.nextInt();

        for (int test = 1; test <= totalTest; test++) {
            // reset
            counts.clear();

            int length1 = in.nextInt();
            int length2 = in.nextInt();
            for (int i = 0; i < length1; i++) {
                int value = in.nextInt();
                int count = counts.getOrDefault(value, 0);
                counts.put(value, count + 1);
            }
            for (int i = 0; i < length2; i++) {
                int value = in.nextInt();
                int count = counts.getOrDefault(value, 0);
                counts.put(value, count - 1);
            }

            int totalRemoved = 0;
            for (int value : counts.values()) {
                int currentTotalRemoved = Math.abs(value);
                totalRemoved += currentTotalRemoved;
            }
            System.out.println(totalRemoved);
        }

        in.close();
    }
}
