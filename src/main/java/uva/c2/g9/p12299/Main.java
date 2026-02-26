package uva.c2.g9.p12299;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.function.Function;

/**
 * 12299 - RMQ with Shifts
 * Time limit: 1.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=3720
 */
public class Main {
    public static void main(String... args) throws IOException {
        FastReader in = new FastReader(System.in, 1 << 16);
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 16));

        int totalNumbers = in.nextInt();
        int totalOperations = in.nextInt();
        int[] numbers = new int[totalNumbers];
        for (int i = 0; i < totalNumbers; i++) numbers[i] = in.nextInt();

        Solution solution = new Solution(numbers);

        for (int i = 0; i < totalOperations; i++) {
            String operation = in.next();
            String[] arguments = operation.substring(6, operation.length() - 1).split(",");
            if (operation.startsWith("query")) {
                int[] range = Arrays.stream(arguments).mapToInt(Integer::parseInt).map(v -> v - 1).toArray();
                int result = solution.query(range[0], range[1]);
                out.println(result);
            } else if (operation.startsWith("shift")) {
                int[] indexes = Arrays.stream(arguments).mapToInt(Integer::parseInt).map(v -> v - 1).toArray();
                solution.shift(indexes);
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

class Solution {
    private final SegmentTree tree;

    public Solution(int[] numbers) {
        this.tree = new SegmentTree(numbers);
    }

    public int query(int from, int to) {
        return tree.min(from, to);
    }

    public void shift(int... indexes) {
        int first = tree.min(indexes[0], indexes[0]);
        for (int i = 0, j = 1; j < indexes.length; i++, j++) {
            int next = tree.get(indexes[j]);
            tree.update(indexes[i], next);
        }
        tree.update(indexes[indexes.length - 1], first);
    }
}

class SegmentTree {
    private static final int ROOT = 1;
    private final int length;
    private final int[] tree;

    public SegmentTree(int[] values) {
        this.length = values.length;
        this.tree = new int[4 * length];
        build(values);
    }

    private void build(int[] values) {
        build(values, ROOT, 0, length - 1);
    }

    private void build(int[] values, int id, int left, int right) {
        if (left == right) {
            tree[id] = values[left];
            return;
        }

        int middle = (left + right) / 2;
        build(values, leftId(id), left, middle);
        build(values, rightId(id), middle + 1, right);
        tree[id] = Math.min(tree[leftId(id)], tree[rightId(id)]);
    }

    public int get(int position) {
        return min(position, position);
    }

    public int min(int from, int to) {
        return min(from, to, ROOT, 0, length - 1);
    }

    private int min(int from, int to, int id, int left, int right) {
        if (from <= left && right <= to) return tree[id];
        if (left > to || right < from) return Integer.MAX_VALUE;

        int middle = (left + right) / 2;
        int leftMin = min(from, to, leftId(id), left, middle);
        int rightMin = min(from, to, rightId(id), middle + 1, right);

        return Math.min(leftMin, rightMin);
    }

    public void update(int position, int value) {
        update(position, value, ROOT, 0, length - 1);
    }

    private void update(int position, int value, int id, int left, int right) {
        if (left == right && left == position) {
            tree[id] = value;
            return;
        }

        int middle = (left + right) / 2;
        if (position <= middle) update(position, value, leftId(id), left, middle);
        else update(position, value, rightId(id), middle + 1, right);

        tree[id] = Math.min(tree[leftId(id)], tree[rightId(id)]);
    }

    private int leftId(int id) {
        return id * 2;
    }

    private int rightId(int id) {
        return id * 2 + 1;
    }
}
