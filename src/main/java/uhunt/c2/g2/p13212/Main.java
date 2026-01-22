package uhunt.c2.g2.p13212;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

/**
 * 13212 - How many inversions?
 * Time limit: 1.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=5135
 */
public class Main {
    public static void main(String... args) throws IOException {
        FastReader in = new FastReader(System.in);
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 26));

        while (true) {
            int totalNumber = in.nextInt();
            if (totalNumber == 0) break;
            int[] numbers = new int[totalNumber];
            for (int i = 0; i < totalNumber; i++) numbers[i] = in.nextInt();

            Solution solution = new Solution(totalNumber, numbers);
            long count = solution.countInversion();

            out.println(count);
        }

        out.flush();
        out.close();
    }
}

class FastReader {
    private final InputStream stream;
    private final byte[] buffer = new byte[1 << 16];
    private int length = 0;
    private int offset = 0;

    public FastReader(InputStream stream) {
        this.stream = stream;
    }

    public int nextInt() throws IOException {
        // skip non-digit
        while (true) {
            if (exhausted()) read();
            if (end()) return 0;
            if (Character.isDigit(buffer[offset])) break;
            offset++;
        }

        // read digit
        int value = 0;
        while (true) {
            if (exhausted()) read();
            if (end()) return value;
            if (!Character.isDigit(buffer[offset])) return value;
            value *= 10;
            value += buffer[offset] - '0';
            offset++;
        }
    }

    private boolean end() {
        return length == -1;
    }

    private boolean exhausted() {
        return offset >= length;
    }

    private void read() throws IOException {
        length = stream.read(buffer);
        offset = 0;
    }
}

class Solution {
    private final int totalNumber;
    private final int[] numbers;

    public Solution(int totalNumber, int[] numbers) {
        this.totalNumber = totalNumber;
        this.numbers = numbers;
    }

    public long countInversion() {
        return countInversionWithMergeSort(numbers, 0, totalNumber - 1, numbers.clone());
    }

    private long countInversionWithMergeSort(int[] numbers, int left, int right, int[] merged) {
        long count = 0;

        // divide and sort
        int mid = (left + right) / 2;
        if (left < mid) count += countInversionWithMergeSort(numbers, left, mid, merged);
        if (mid + 1 < right) count += countInversionWithMergeSort(numbers, mid + 1, right, merged);

        // merge
        int pLeft = left;
        int pRight = mid + 1;
        for (int i = left; i <= right; i++) {
            boolean rightExhausted = pRight > right;
            boolean leftExhausted = pLeft > mid;
            boolean leftLessThanRight = !leftExhausted && !rightExhausted && numbers[pLeft] <= numbers[pRight];

            if (rightExhausted || leftLessThanRight) {
                merged[i] = numbers[pLeft++];
            } else {
                int leftTotal = (mid + 1 - pLeft);
                count += leftTotal;
                merged[i] = numbers[pRight++];
            }
        }

        System.arraycopy(merged, left, numbers, left, right - left + 1);
        return count;
    }
}
