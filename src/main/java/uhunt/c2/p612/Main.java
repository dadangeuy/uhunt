package uhunt.c2.p612;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Scanner;

/**
 * 612 - DNA Sorting
 * Time limit: 3.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=553
 */
public class Main {
    public static void main(String... args) {
        Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 16));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 16));

        int totalDataset = in.nextInt();
        for (int i = 0; i < totalDataset; i++) {
            int dnaLength = in.nextInt();
            int totalDna = in.nextInt();

            String[] dnas = new String[totalDna];
            for (int j = 0; j < totalDna; j++) dnas[j] = in.next();

            Solution solution = new Solution(dnaLength, totalDna, dnas);
            String[] sortedDnas = solution.sortByLeastInversion();

            if (i > 0) out.println();
            for (String dna : sortedDnas) out.println(dna);
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Solution {
    private static final HashMap<String, Integer> INVERSIONS = new HashMap<>();
    private final int dnaLength;
    private final int totalDna;
    private final String[] dnas;

    public Solution(int dnaLength, int totalDna, String[] dnas) {
        this.dnaLength = dnaLength;
        this.totalDna = totalDna;
        this.dnas = dnas;
    }

    public String[] sortByLeastInversion() {
        Arrays.sort(dnas, Comparator.comparingInt(this::getInversion));
        return dnas;
    }

    private int getInversion(String dna) {
        return INVERSIONS.computeIfAbsent(dna, dnaKey -> {
            int inversion = 0;
            for (int i = 0; i < dnaKey.length() - 1; i++) {
                for (int j = i + 1; j < dnaKey.length(); j++) {
                    if (dnaKey.charAt(i) > dnaKey.charAt(j)) {
                        ++inversion;
                    }
                }
            }
            return inversion;
        });
    }
}
