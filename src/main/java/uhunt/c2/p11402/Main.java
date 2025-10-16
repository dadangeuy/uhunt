package uhunt.c2.p11402;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.BitSet;
import java.util.function.Function;

/**
 * 11402 - Ahoy, Pirates!
 * Time limit: 5.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=2397
 */
public class Main {
    public static void main(String... args) throws IOException {
        FastReader in = new FastReader(System.in);
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 16));

        int totalCase = in.nextInt();
        for (int i = 0; i < totalCase; i++) {
            StringBuilder bPirates = new StringBuilder();
            int totalPair = in.nextInt();
            for (int j = 0; j < totalPair; j++) {
                int concatAmount = in.nextInt();
                String pairPirates = in.next();
                for (int k = 0; k < concatAmount; k++) bPirates.append(pairPirates);
            }
            char[] pirates = bPirates.toString().toCharArray();

            PirateLand land = new PirateLand(pirates);

            out.format("Case %d:\n", i + 1);

            int totalQuery = in.nextInt();
            for (int j = 0, k = 0; j < totalQuery; j++) {
                char action = in.next().charAt(0);
                int from = in.nextInt();
                int to = in.nextInt();

                switch (action) {
                    case PirateLand.CONVERT_TO_BUCCANEER:
                        land.convertToBuccaneer(from, to);
                        break;
                    case PirateLand.CONVERT_TO_BARBARY:
                        land.convertToBarbary(from, to);
                        break;
                    case PirateLand.INVERSE:
                        land.inverse(from, to);
                        break;
                    case PirateLand.GET_TOTAL_BUCCANEER:
                        int total = land.getTotalBuccaneer(from, to);
                        out.format("Q%d: %d\n", ++k, total);
                        break;
                }
            }
        }

        in.close();
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
        skipUntil(Character::isDigit);

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

class PirateLand {
    // action
    public static final char CONVERT_TO_BUCCANEER = 'F';
    public static final char CONVERT_TO_BARBARY = 'E';
    public static final char INVERSE = 'I';
    public static final char GET_TOTAL_BUCCANEER = 'S';
    // pirate
    public static final char BARBARY = '0';
    public static final char BUCCANEER = '1';
    public static final boolean BARBARY_BIT = false;
    public static final boolean BUCCANEER_BIT = true;

    private final BitSet pirates;

    public PirateLand(char[] pirates) {
        this.pirates = createBitSet(pirates);
    }

    private BitSet createBitSet(char[] pirates) {
        BitSet set = new BitSet(pirates.length);
        for (int i = 0; i < pirates.length; i++)
            if (pirates[i] == BUCCANEER) set.set(i);
        return set;
    }

    public void convertToBuccaneer(int from, int to) {
        pirates.set(from, to + 1, BUCCANEER_BIT);
    }

    public void convertToBarbary(int from, int to) {
        pirates.set(from, to + 1, BARBARY_BIT);
    }

    public void inverse(int from, int to) {
        pirates.flip(from, to + 1);
    }

    public int getTotalBuccaneer(int from, int to) {
        return pirates.get(from, to + 1).cardinality();
    }
}
