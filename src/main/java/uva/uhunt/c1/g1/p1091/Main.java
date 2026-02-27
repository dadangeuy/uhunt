package uva.uhunt.c1.g1.p1091;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String... args) {
        Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 16));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 16));

        for (int i = 1; in.hasNextInt(); i++) {
            int totalSensor = in.nextInt();
            if (totalSensor == 0) break;

            int[] sensors = new int[totalSensor];
            for (int j = 0; j < totalSensor; j++) sensors[j] = in.nextInt();

            Solution solution = new Solution(totalSensor, sensors);
            String decode = solution.decode();

            out.format("Case %d: %s\n", i, decode);
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Solution {
    // encoding scheme
    private static final String START_STOP = "00110";
    private static final Map<String, Character> decodes = new HashMap<String, Character>() {{
        put("00001", '0');
        put("10001", '1');
        put("01001", '2');
        put("11000", '3');
        put("00101", '4');
        put("10100", '5');
        put("01100", '6');
        put("00011", '7');
        put("10010", '8');
        put("10000", '9');
        put("00100", '-');
    }};

    // error
    private static final Exception BAD_CODE = new Exception("bad code");
    private static final Exception BAD_C = new Exception("bad C");
    private static final Exception BAD_K = new Exception("bad K");

    // input
    private final int totalSensor;
    private final int[] sensors;

    public Solution(int totalSensor, int[] sensors) {
        this.totalSensor = totalSensor;
        this.sensors = sensors;
    }

    public String decode() {
        try {
            return doDecode();
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    private String doDecode() throws Exception {
        validateLength();
        validateWidth();

        int[] rangeNarrow = getRangeNarrow();
        int[] rangeWide = getRangeWide();
        String barcode = parseBarcode(sensors, rangeNarrow, rangeWide);

        barcode = fixOrientation(barcode);
        validateStartStop(barcode);
        validateSeparator(barcode);

        String label = parseLabel(barcode);
        validateC(label);
        validateK(label);

        return label.substring(0, label.length() - 2);
    }

    private void validateLength() throws Exception {
        if (totalSensor % 6 != 5) throw BAD_CODE;
        if ((totalSensor + 1) / 6 < 5) throw BAD_CODE;
    }

    private void validateWidth() throws Exception {
        int min = min(sensors);
        int max = max(sensors);
        int div = min + max;

        int wmin = Integer.MAX_VALUE;
        int wmax = Integer.MIN_VALUE;
        for (int sensor : sensors) {
            int width = sensor * 2 < div ? sensor * 2 : sensor;
            wmin = Math.min(wmin, width);
            wmax = Math.max(wmax, width);
        }

        // exceed 5% error margin
        if (wmax * 95 > wmin * 105) throw BAD_CODE;
    }

    private String parseBarcode(int[] sensors, int[] rangeNarrow, int[] rangeWide) {
        StringBuilder builder = new StringBuilder();
        for (int sensor : sensors) {
            if (rangeNarrow[0] <= sensor && sensor <= rangeNarrow[1]) builder.append(0);
            else if (rangeWide[0] <= sensor && sensor <= rangeWide[1]) builder.append(1);
            else throw new RuntimeException("should be handled by validateWidth() function.");
        }
        return builder.toString();
    }

    private int[] getRangeNarrow() {
        int min = min(sensors);
        int max = max(sensors);
        int div = min + max;

        int maxNarrow = min;
        for (int sensor : sensors) {
            if (sensor * 2 < div) maxNarrow = Math.max(maxNarrow, sensor);
        }

        return new int[]{min, maxNarrow};
    }

    private int[] getRangeWide() {
        int min = min(sensors);
        int max = max(sensors);
        int div = min + max;

        int minWide = max;
        for (int sensor : sensors) {
            if (sensor * 2 >= div) minWide = Math.min(minWide, sensor);
        }

        return new int[]{minWide, max};
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

    private String fixOrientation(String barcode) throws Exception {
        if (barcode.startsWith(START_STOP)) return barcode;
        if (barcode.startsWith(reverse(START_STOP))) return reverse(barcode);
        throw BAD_CODE;
    }

    private String reverse(String text) {
        StringBuilder sb = new StringBuilder(text.length());
        for (int i = text.length() - 1; i >= 0; i--) sb.append(text.charAt(i));
        return sb.toString();
    }

    private void validateStartStop(String barcode) throws Exception {
        boolean hasStart = barcode.startsWith(START_STOP);
        boolean hasStop = barcode.endsWith(START_STOP);
        if (!hasStart || !hasStop) throw BAD_CODE;
    }

    private void validateSeparator(String barcode) throws Exception {
        for (int i = 5; i < barcode.length(); i += 6) {
            if (barcode.charAt(i) != '0') throw BAD_CODE;
        }
    }

    private String parseLabel(String barcode) throws Exception {
        StringBuilder sb = new StringBuilder();
        for (int i = 6; i < barcode.length() - 5; i += 6) {
            String bi = barcode.substring(i, i + 5);
            if (!decodes.containsKey(bi)) throw BAD_CODE;
            char value = decodes.get(bi);
            sb.append(value);
        }
        return sb.toString();
    }

    private void validateC(String label) throws Exception {
        int sum = 0;
        int n = label.length() - 2;
        for (int i = 1; i <= n; i++) {
            int w = weight(label.charAt(i - 1));
            int cur = (((n - i) % 10) + 1) * w;
            sum = (sum + cur) % 11;
        }

        int expectedC = sum;
        int actualC = weight(label.charAt(label.length() - 2));
        if (expectedC != actualC) throw BAD_C;
    }

    private void validateK(String label) throws Exception {
        int sum = 0;
        int n = label.length() - 2;
        for (int i = 1; i <= n + 1; i++) {
            int w = weight(label.charAt(i - 1));
            int cur = (((n - i + 1) % 9) + 1) * w;
            sum = (sum + cur) % 11;
        }

        int expectedK = sum;
        int actualK = weight(label.charAt(label.length() - 1));
        if (expectedK != actualK) throw BAD_K;
    }

    private int weight(char c) {
        return c == '-' ? 10 : c - '0';
    }
}
