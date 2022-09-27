package dev.rizaldi.uhunt.c2.p1513;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

/**
 * 1513 - Movie collection
 * Time limit: 3.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=4259
 */
public class Main {
    public static void main(String... args) {
        Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 16));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 16));

        int totalTests = in.nextInt();
        for (int i = 0; i < totalTests; i++) {
            int totalMovies = in.nextInt();
            MovieStack stack = new MovieStack(totalMovies);

            int totalRequests = in.nextInt();
            for (int j = 0; j < totalRequests; j++) {
                int movie = in.nextInt();
                int totalAbove = stack.moveToTop(movie);

                if (j > 0) out.print(' ');
                out.print(totalAbove);
            }
            out.println();
        }

        in.close();
        out.flush();
        out.close();
    }
}

final class BinaryIndexedTree {
    private final int[] sumTree;

    public BinaryIndexedTree(int len) {
        sumTree = new int[len];
    }

    public BinaryIndexedTree(int[] sumTree) {
        this.sumTree = sumTree;
    }

    public int length() {
        return sumTree.length;
    }

    public int get(int index) {
        if (!(0 <= index && index < length())) throw new IndexOutOfBoundsException();
        int result = sumTree[index];
        // For each consecutive 1 in the lowest order bits of index
        for (int i = 1; (index & i) != 0; i <<= 1)
            result -= sumTree[index ^ i];
        return result;
    }

    public void set(int index, long val) {
        if (!(0 <= index && index < length())) throw new IndexOutOfBoundsException();
        add(index, val - get(index));
    }

    public void add(int index, long delta) {
        if (!(0 <= index && index < length())) throw new IndexOutOfBoundsException();
        do {
            sumTree[index] += delta;
            index |= index + 1;  // Set lowest 0 bit; strictly increasing
            // Equivalently: index |= Integer.lowestOneBit(~index);
        } while (index < sumTree.length);
    }

    public int getTotal() {
        return getPrefixSum(length());
    }

    /**
     * @param end - end of range, exclusive
     * @return range sum from 0 to end
     */
    public int getPrefixSum(int end) {
        if (!(0 <= end && end <= length())) throw new IndexOutOfBoundsException();
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
        if (!(0 <= start && start <= end && end <= length())) throw new IndexOutOfBoundsException();
        return getPrefixSum(end) - getPrefixSum(start);
    }
}

final class MovieStack {
    private static final int[] _SUM_TREE = new int[200001];
    private static final int[] _POSITION = new int[100001];
    private final int totalMovies;
    private final BinaryIndexedTree tree;
    private final int[] positions;
    private int lastPosition;

    public MovieStack(int totalMovies) {
        this.totalMovies = totalMovies;

        Arrays.fill(_SUM_TREE, 0);
        this.tree = new BinaryIndexedTree(_SUM_TREE);
        this.positions = _POSITION;
        this.lastPosition = totalMovies;

        for (int movie = 1; movie <= totalMovies; movie++) {
            positions[movie] = totalMovies - movie + 1;
            tree.add(positions[movie], 1);
        }
    }

    public int moveToTop(int movie) {
        int totalBelow = tree.getPrefixSum(positions[movie] + 1);
        int totalAbove = totalMovies - totalBelow;
        tree.add(positions[movie], -1);
        positions[movie] = ++lastPosition;
        tree.add(positions[movie], 1);
        return totalAbove;
    }
}
