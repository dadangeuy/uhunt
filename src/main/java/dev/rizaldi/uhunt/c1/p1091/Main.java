package dev.rizaldi.uhunt.c1.p1091;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {
    public static void main(String... args) {
        Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 16));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 16));

        for (int i = 1; ; i++) {
            int totalSensor = in.nextInt();
            if (totalSensor == 0) break;

            int[] sensors = new int[totalSensor];
            for (int j = 0; j < totalSensor; j++) sensors[j] = in.nextInt();

            Solution solution = new Solution(totalSensor, sensors);
            String decode = solution.decode();

            out.format("Case %d: %s\n", i, decode);
        }

        in.close();
        out.close();
    }
}

/**
 * - `bad C` if C check failed
 * - `bad K` if K check failed
 * - `bad code` if unable to decode, invalid bars, missing start/stop
 * - try decode left-right and right-left
 */
class Solution {
    private static final int[] ZERO = new int[]{0, 0, 0, 0, 1};
    private static final int[] ONE = new int[]{1, 0, 0, 0, 1};
    private static final int[] TWO = new int[]{0, 1, 0, 0, 1};
    private static final int[] THREE = new int[]{1, 1, 0, 0, 0};
    private static final int[] FOUR = new int[]{0, 0, 1, 0, 1};
    private static final int[] FIVE = new int[]{1, 0, 1, 0, 0};
    private static final int[] SIX = new int[]{0, 1, 1, 0, 0};
    private static final int[] SEVEN = new int[]{0, 0, 0, 1, 1};
    private static final int[] EIGHT = new int[]{1, 0, 0, 1, 0};
    private static final int[] NINE = new int[]{1, 0, 0, 0, 0};
    private static final int[] DASH = new int[]{0, 0, 1, 0, 0};
    private static final int[] START_STOP = new int[]{0, 0, 1, 1, 0};

    private static final Exception BAD_CODE = new Exception("bad code");
    private static final Exception BAD_C = new Exception("bad C");
    private static final Exception BAD_K = new Exception("bad K");

    private final int totalSensor;
    private final int[] sensors;

    public Solution(int totalSensor, int[] sensors) {
        this.totalSensor = totalSensor;
        this.sensors = sensors;
    }

    public String decode() {
        try {
            // validate length
            if (sensors.length % 6 != 5) return "bad code";
            if ((sensors.length + 1) / 6 < 5) return "bad code";

            if (!validMargin(sensors)) return "bad code";
            int[] bars = convertToBars(sensors);
            bars = fixOrientation(bars);
            if (!hasStartStop(bars)) return "bad code";

            StringBuilder decodeBuilder = new StringBuilder();
            for (int i = 6; i < bars.length - 6; i += 6) {
                if (equals(bars, i, ZERO)) {
                    decodeBuilder.append(0);
                } else if (equals(bars, i, ONE)) {
                    decodeBuilder.append(1);
                } else if (equals(bars, i, TWO)) {
                    decodeBuilder.append(2);
                } else if (equals(bars, i, THREE)) {
                    decodeBuilder.append(3);
                } else if (equals(bars, i, FOUR)) {
                    decodeBuilder.append(4);
                } else if (equals(bars, i, FIVE)) {
                    decodeBuilder.append(5);
                } else if (equals(bars, i, SIX)) {
                    decodeBuilder.append(6);
                } else if (equals(bars, i, SEVEN)) {
                    decodeBuilder.append(7);
                } else if (equals(bars, i, EIGHT)) {
                    decodeBuilder.append(8);
                } else if (equals(bars, i, NINE)) {
                    decodeBuilder.append(9);
                } else if (equals(bars, i, DASH)) {
                    decodeBuilder.append('-');
                } else {
                    throw BAD_CODE;
                }
            }

            // check length
            if (decodeBuilder.length() < 3) throw BAD_CODE;

            String decode = decodeBuilder.toString();
            String value = decode.substring(0, decode.length() - 2);
            int c = weight(decode.charAt(decode.length() - 2));
            int k = weight(decode.charAt(decode.length() - 1));

            if (!validC(value, c)) throw BAD_C;
            if (!validK(value, k)) throw BAD_K;

            return value;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    private int[] fixOrientation(int[] bars) throws Exception {
        boolean startLeft = equals(bars, 0, START_STOP);
        if (startLeft) return bars;

        bars = reverse(bars);
        boolean startRight = equals(bars, 0, START_STOP);
        if (startRight) return bars;

        throw BAD_CODE;
    }

    private int[] convertToBars(int[] sensors) {
        int min = min(sensors);
        int max = max(sensors);
        double div = (min + max) / 2.0;

        int[] bars = new int[sensors.length];
        for (int i = 0; i < sensors.length; i++) {
            bars[i] = sensors[i] <= div ? 0 : 1;
        }
        return bars;
    }

    private boolean validMargin(int[] sensors) {
        int min0 = min(sensors), max0 = min0;
        int max1 = max(sensors), min1 = max1;
        int div = (min0 + max1) / 2;
        for (int sensor : sensors) {
            if (sensor <= div) max0 = Math.max(max0, sensor);
            else min1 = Math.min(min1, sensor);
        }

        if (min0 * 105 < max0 * 95) return false;
        if (min1 * 105 < max1 * 95) return false;
        if (min0 * 2 * 105 < max1 * 95) return false;
        return min1 * 105 >= max0 * 2 * 95;
    }

    private boolean hasStartStop(int[] bars) {
        boolean hasStart = equals(bars, 0, START_STOP);
        boolean hasStop = equals(bars, bars.length - 5, START_STOP);
        return hasStart && hasStop;
    }

    private int min(int... array) {
        int min = array[0];
        for (int value : array) min = Math.min(min, value);
        return min;
    }

    private int max(int... array) {
        int max = array[0];
        for (int value : array) max = Math.max(max, value);
        return max;
    }

    private boolean between(int left, int mid, int right) {
        return left <= mid && mid <= right;
    }

    private boolean equals(int[] array, int offset, int[] target) {
        if (offset + target.length > array.length) return false;
        for (int i = 0; i < target.length; i++)
            if (array[offset + i] != target[i])
                return false;
        return true;
    }

    private int[] reverse(int[] array) {
        int[] reverseArray = array.clone();
        for (int i = 0, j = array.length - 1; i < j; i++, j--) {
            reverseArray[i] = array[j];
            reverseArray[j] = array[i];
        }
        return reverseArray;
    }

    private boolean validC(String value, int c) {
        int expectedC = calculateC(value);
        return c == expectedC;
    }

    private boolean validK(String value, int k) {
        int expectedK = calculateK(value);
        return k == expectedK;
    }

    private int calculateC(String value) {
        int sum = 0;
        int n = value.length();
        for (int i = 1; i <= n; i++) {
            int w = weight(value.charAt(i - 1));
            int vi = ((n - i) % 10 + 1) * w;
            sum += vi;
        }
        return sum % 11;
    }

    private int calculateK(String value) {
        int sum = 0;
        int n = value.length();
        for (int i = 1; i <= n; i++) {
            int w = weight(value.charAt(i - 1));
            int vi = ((n - i + 1) % 9 + 1) * w;
            sum += vi;
        }
        sum += ((n - (n + 1) + 1) % 9 + 1) * calculateC(value);
        return sum % 11;
    }

    private int weight(char c) {
        if (c == '-') return 10;
        return c - '0';
    }
}
