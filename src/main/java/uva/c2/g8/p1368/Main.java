package uva.c2.g8.p1368;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String... args) {
        Scanner in = new Scanner(new BufferedInputStream(System.in, 2 << 16));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 2 << 16));

        int totalCase = in.nextInt();
        for (int i = 0; i < totalCase; i++) {
            int total = in.nextInt();
            int length = in.nextInt();

            char[][] DNAs = new char[total][];
            for (int j = 0; j < total; j++) {
                char[] DNA = in.next().toCharArray();
                DNAs[j] = DNA;
            }

            Solution solution = new Solution(total, length, DNAs);
            Answer answer = solution.getConsensusDNA();

            out.println(answer.consensusDNA);
            out.println(answer.totalError);
        }

        in.close();
        out.flush();
        out.close();
    }
}

/**
 * 1) for each index, count hamming distance of each character (ACGT)
 * 2) append character that has max count to consensus DNA
 * 3) time complexity: O(length * total)
 */
class Solution {
    private static final char adenine = 'A';
    private static final char cytosine = 'C';
    private static final char guanine = 'G';
    private static final char thymine = 'T';
    private static final char[] strands = new char[]{adenine, cytosine, guanine, thymine};
    private final int total;
    private final int length;
    private final char[][] DNAs;

    public Solution(int total, int length, char[][] DNAs) {
        this.total = total;
        this.length = length;
        this.DNAs = DNAs;
    }

    public Answer getConsensusDNA() {
        char[] consensusDNA = new char[length];
        int totalError = 0;

        DirectAddressTable counts = new DirectAddressTable();
        for (int i = 0; i < length; i++) {
            counts.clear();

            for (char[] DNA : DNAs) {
                char strand = DNA[i];
                counts.set(strand, counts.get(strand) + 1);
            }

            int maxCount = -1;
            for (char strand : strands) {
                int count = counts.get(strand);
                if (count > maxCount) {
                    maxCount = count;
                    consensusDNA[i] = strand;
                }
            }

            totalError += DNAs.length - maxCount;
        }

        return new Answer(consensusDNA, totalError);
    }
}

class DirectAddressTable {
    private final int[] table = new int[4];

    public void set(int key, int value) {
        int address = address(key);
        table[address] = value;
    }

    public int get(int key) {
        int address = address(key);
        return table[address];
    }

    public void clear() {
        Arrays.fill(table, 0);
    }

    private int address(int key) {
        switch (key) {
            case 'A':
                return 0;
            case 'C':
                return 1;
            case 'G':
                return 2;
            case 'T':
                return 3;
        }
        return -1;
    }
}

class Answer {
    public final char[] consensusDNA;
    public final int totalError;

    public Answer(char[] consensusDNA, int totalError) {
        this.consensusDNA = consensusDNA;
        this.totalError = totalError;
    }
}
