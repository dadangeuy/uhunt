package uva.c2.g1.p501;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Comparator;
import java.util.Scanner;
import java.util.TreeSet;
import java.util.stream.IntStream;

/**
 * 501 - Black Box
 * Time limit: 3.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&page=show_problem&problem=442
 */
public class Main {
    public static void main(final String... args) throws IOException {
        final Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 16));
        final PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 16));

        final int totalDataset = in.nextInt();
        for (int dataset = 0; dataset < totalDataset; dataset++) {
            if (dataset > 0) out.write('\n');

            final int totalAddOperations = in.nextInt();
            final int totalGetOperations = in.nextInt();

            final int[] addElements = IntStream.range(0, totalAddOperations)
                    .map(i -> in.nextInt())
                    .toArray();
            final int[] getTrxId = IntStream.range(0, totalGetOperations)
                    .map(i -> in.nextInt())
                    .map(id -> id - 1)
                    .toArray();

            final SplitTree tree = new SplitTree();
            for (int trxId = 0, getId = 0; trxId < addElements.length; trxId++) {
                tree.add(addElements[trxId]);
                while (getId < getTrxId.length && getTrxId[getId] == trxId) {
                    final int element = tree.get();
                    out.write(Integer.toString(element));
                    out.write('\n');
                    getId++;
                }
            }
        }

        in.close();
        out.flush();
        out.close();
    }
}

class SplitTree {
    private static final Comparator<int[]> COMPARE_BY_ELEMENT_AND_TRX_ID = Comparator
            .comparingInt((int[] p) -> p[0])
            .thenComparingInt((int[] p) -> p[1]);
    private final TreeSet<int[]> lesserSet = new TreeSet<>(COMPARE_BY_ELEMENT_AND_TRX_ID);
    private final TreeSet<int[]> greaterSet = new TreeSet<>(COMPARE_BY_ELEMENT_AND_TRX_ID);

    public void add(int element) {
        final int trxId = lesserSet.size() + greaterSet.size();
        greaterSet.add(new int[]{element, trxId});

        while (
                !lesserSet.isEmpty() &&
                !greaterSet.isEmpty() &&
                COMPARE_BY_ELEMENT_AND_TRX_ID.compare(lesserSet.last(), greaterSet.first()) > 0
        ) {
            final int[] leftEntry = lesserSet.last();
            final int[] rightEntry = greaterSet.first();

            lesserSet.remove(leftEntry);
            greaterSet.remove(rightEntry);

            lesserSet.add(rightEntry);
            greaterSet.add(leftEntry);
        }
    }

    public int get() {
        final int[] entry = greaterSet.first();
        greaterSet.remove(entry);
        lesserSet.add(entry);
        return entry[0];
    }
}
