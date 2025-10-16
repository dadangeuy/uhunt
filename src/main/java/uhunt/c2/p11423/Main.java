package uhunt.c2.p11423;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.function.Function;

/**
 * 11423 - Cache Simulator
 * Time limit: 10.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=2418
 */
public class Main {
    public static void main(String... args) throws IOException {
        FastReader in = new FastReader(System.in, 1 << 16);
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 16));

        int totalCaches = in.nextInt();
        int[] cacheSizes = new int[totalCaches];
        for (int i = 0; i < totalCaches; i++) cacheSizes[i] = in.nextInt();

        Solution solution = new Solution(cacheSizes);

        for (char o = in.next().charAt(0); o != 'E'; o = in.next().charAt(0)) {
            switch (o) {
                case 'A': // addr
                    solution.addr(in.nextInt());
                    break;
                case 'R': // range
                    solution.range(in.nextInt(), in.nextInt(), in.nextInt());
                    break;
                case 'S': // stat
                    int[] stat = solution.stat();
                    out.print(stat[0]);
                    for (int i = 1; i < stat.length; i++) {
                        out.print(' ');
                        out.print(stat[i]);
                    }
                    out.println();
                    break;
            }
        }

        in.close();
        out.flush();
        out.close();
    }
}

class FastReader {
    private final InputStream stream;
    private final byte[] buffer;
    private int length = 0;
    private int offset = 0;

    public FastReader(InputStream stream, int bufferSize) {
        this.stream = stream;
        this.buffer = new byte[bufferSize];
    }

    public int nextInt() throws IOException {
        skipUntil(b -> Character.isDigit(b) || b == '-');
        boolean negative = buffer[offset] == '-';
        if (negative) offset++;

        int value = 0;
        while (true) {
            if (exhausted()) read();
            if (end()) return value;
            if (!Character.isDigit(buffer[offset])) return negative ? -value : value;
            value *= 10;
            value += buffer[offset] - '0';
            offset++;
        }
    }

    public String next() throws IOException {
        skipUntil(b -> !Character.isWhitespace(b));

        StringBuilder value = new StringBuilder();
        while (true) {
            if (exhausted()) read();
            if (end()) return value.toString();
            if (Character.isWhitespace(buffer[offset])) return value.toString();
            value.append((char) buffer[offset]);
            offset++;
        }
    }

    private void skipUntil(Function<Byte, Boolean> criteria) throws IOException {
        while (true) {
            if (exhausted()) read();
            if (end()) return;
            if (criteria.apply(buffer[offset])) return;
            ++offset;
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

    public void close() throws IOException {
        stream.close();
    }
}

class Constraint {
    public static final int TOTAL_CACHE_MIN = 1;
    public static final int TOTAL_CACHE_MAX = 30;
    public static final int CACHE_SIZE_MIN = 2;
    public static final int CACHE_SIZE_MAX = 1_048_576; // 2^20
    public static final int TOTAL_ACCESS_MAX = 10_000_000; // 10^7
    public static final int ADDRESS_MIN = 0;
    public static final int ADDRESS_MAX = 16777215; // 2^24 - 1 = 2x10^7
    public static final int TOTAL_OPERATION_MAX = 20_000;
}

class Solution {
    private final int[] sizes;
    private final int[] stat;
    private final int[] _stat;
    private final FenwickTree accessTimes;
    private final int[] prevTimes = new int[Constraint.ADDRESS_MAX + 1];
    private int currTime = 0;

    public Solution(int[] sizes) {
        this.sizes = sizes;
        this.stat = new int[sizes.length];
        this._stat = new int[sizes.length];
        this.accessTimes = new FenwickTree(Constraint.TOTAL_ACCESS_MAX);
        Arrays.fill(prevTimes, -1);
    }

    // O(1)
    public void addr(int x) {
        int prevTime = prevTimes[x];
        prevTimes[x] = currTime;
        boolean hasAccess = prevTime != -1;
        if (hasAccess) accessTimes.add(prevTime, 1);
        int minCacheSize = hasAccess ? currTime - prevTime - accessTimes.getRangeSum(prevTime + 1, currTime) : Integer.MAX_VALUE;
        for (int cacheId = 0; cacheId < sizes.length; cacheId++) {
            boolean miss = sizes[cacheId] < minCacheSize;
            if (miss) stat[cacheId]++;
        }
        currTime++;
    }

    // O(n)
    public void range(int b, int y, int n) {
        for (int k = 0; k < n; k++) {
            int x = b + y * k;
            addr(x);
        }
    }

    // O(total cache)
    public int[] stat() {
        System.arraycopy(stat, 0, _stat, 0, stat.length);
        Arrays.fill(stat, 0);
        return _stat;
    }
}

final class FenwickTree {
    private final int length;
    private final int[] sumTree;

    public FenwickTree(int length) {
        this(new int[length], length);
    }

    public FenwickTree(int[] sumTree, int length) {
        this.sumTree = sumTree;
        this.length = sumTree.length;
    }

    public int length() {
        return length;
    }

    public int get(int index) {
        int result = sumTree[index];
        // For each consecutive 1 in the lowest order bits of index
        for (int i = 1; (index & i) != 0; i <<= 1)
            result -= sumTree[index ^ i];
        return result;
    }

    public void set(int index, long val) {
        add(index, val - get(index));
    }

    public void add(int index, long delta) {
        while (index < length()) {
            sumTree[index] += delta;
            index |= index + 1;  // Set lowest 0 bit; strictly increasing
            // Equivalently: index |= Integer.lowestOneBit(~index);
        }
    }

    public int getTotal() {
        return getPrefixSum(length());
    }

    /**
     * @param end - end of range, exclusive
     * @return range sum from 0 to end
     */
    public int getPrefixSum(int end) {
        int result = 0;
        while (end > 0) {
            result += sumTree[end - 1];
            end &= end - 1;  // Clear lowest 1 bit; strictly decreasing
            // Equivalently: end ^= Integer.lowestOneBit(end);
        }
        return result;
    }


    /**
     * @param start - start of range, inclusive
     * @param end   - end of range, exclusive
     * @return range sum from start to end
     */
    public int getRangeSum(int start, int end) {
        return getPrefixSum(end) - getPrefixSum(start);
    }
}
