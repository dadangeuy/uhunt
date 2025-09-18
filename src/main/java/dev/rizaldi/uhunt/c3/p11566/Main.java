package dev.rizaldi.uhunt.c3.p11566;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;

/**
 * 11566 - Let's Yum Cha!
 * Time limit: 1.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&page=show_problem&problem=2613
 */
public class Main {
    public static void main(final String... args) throws IOException {
        final BufferedReader in = new BufferedReader(new InputStreamReader(System.in), 1 << 16);
        final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out), 1 << 16);
        final Process process = new Process();

        while (true) {
            final String[] l1 = Util.readSplitLine(in);
            final int totalFriends = Integer.parseInt(l1[0]);
            final int maxPaidPerPerson = Integer.parseInt(l1[1]);
            final int teaPrice = Integer.parseInt(l1[2]);
            final int totalDimsumKinds = Integer.parseInt(l1[3]);

            final boolean isEOF = totalFriends == 0 && maxPaidPerPerson == 0 && teaPrice == 0 && totalDimsumKinds == 0;
            if (isEOF) break;

            final int[] pricePerDimsumKind = new int[totalDimsumKinds];
            final int[][] favourPerDimsumKindAndPerson = new int[totalDimsumKinds][totalFriends + 1];
            for (int dimsumKindId = 0; dimsumKindId < totalDimsumKinds; dimsumKindId++) {
                final String[] l2 = Util.readSplitLine(in);
                pricePerDimsumKind[dimsumKindId] = Integer.parseInt(l2[0]);
                for (int personId = 0; personId < totalFriends + 1; personId++) {
                    favourPerDimsumKindAndPerson[dimsumKindId][personId] = Integer.parseInt(l2[1 + personId]);
                }
            }

            final Input input = new Input(
                    totalFriends,
                    maxPaidPerPerson,
                    teaPrice,
                    totalDimsumKinds,
                    pricePerDimsumKind,
                    favourPerDimsumKindAndPerson
            );
            final Output output = process.process(input);

            out.write(String.format("%.2f\n", output.optimalMeanFavour));
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Input {
    public final int totalFriends;
    public final int maxPaidPerPerson;
    public final int teaPrice;
    public final int totalDimsumKinds;
    public final int[] pricePerDimsumKind;
    public final int[][] favourPerDimsumKindAndPerson;

    public Input(
            final int totalFriends,
            final int maxPaidPerPerson,
            final int teaPrice,
            final int totalDimsumKinds,
            final int[] pricePerDimsumKind,
            final int[][] favourPerDimsumKindAndPerson
    ) {
        this.totalFriends = totalFriends;
        this.maxPaidPerPerson = maxPaidPerPerson;
        this.teaPrice = teaPrice;
        this.totalDimsumKinds = totalDimsumKinds;
        this.pricePerDimsumKind = pricePerDimsumKind;
        this.favourPerDimsumKindAndPerson = favourPerDimsumKindAndPerson;
    }
}

class Output {
    public final double optimalMeanFavour;

    public Output(final double optimalMeanFavour) {
        this.optimalMeanFavour = optimalMeanFavour;
    }
}

class Process {
    private static final int NONE = -1;

    public Output process(final Input input) {
        final int totalPerson = input.totalFriends + 1;
        final int maxTotalPaid = input.maxPaidPerPerson * totalPerson;
        final int maxTotalDishes = 2 * totalPerson;

        // knapsack total dishes, total paid and total favour
        final int[][] knapsack = new int[maxTotalDishes + 1][maxTotalPaid + 1];
        for (int i = 0; i < knapsack.length; i++) {
            for (int j = 0; j < knapsack[i].length; j++) {
                knapsack[i][j] = NONE;
            }
        }

        // order tea for each person
        final int totalTeaPaid = input.teaPrice * totalPerson;
        if (totalTeaPaid <= maxTotalPaid) {
            knapsack[0][totalTeaPaid] = 0;
        }

        // for each dimsum kind, try order once or twice for each possible total dishes & total paid
        for (int dimsumKind = 0; dimsumKind < input.totalDimsumKinds; dimsumKind++) {
            final int dimsumKindPrice = input.pricePerDimsumKind[dimsumKind];
            final int dimsumKindFavour = Arrays.stream(input.favourPerDimsumKindAndPerson[dimsumKind]).sum();

            for (int totalDishes = maxTotalDishes; totalDishes >= 0; totalDishes--) {
                for (int totalPaid = maxTotalPaid; totalPaid >= 0; totalPaid--) {
                    for (int totalOrder = 2; totalOrder >= 1; totalOrder--) {
                        final int totalFavour = knapsack[totalDishes][totalPaid];
                        if (totalFavour == NONE) continue;

                        final int newTotalFavour = totalFavour + (dimsumKindFavour * totalOrder);
                        final int newTotalPaid = totalPaid + (dimsumKindPrice * totalOrder);
                        final int newTotalDishes = totalDishes + totalOrder;

                        if (newTotalPaid > maxTotalPaid) continue;
                        if (newTotalDishes > maxTotalDishes) continue;

                        final int oldTotalFavour = knapsack[newTotalDishes][newTotalPaid];
                        if (oldTotalFavour == NONE || newTotalFavour > oldTotalFavour) {
                            knapsack[newTotalDishes][newTotalPaid] = newTotalFavour;
                        }
                    }
                }
            }
        }

        int maxTotalFavour = 0;
        for (int totalDishes = 0; totalDishes <= maxTotalDishes; totalDishes++) {
            for (int totalPaid = 0; totalPaid <= maxTotalPaid; totalPaid++) {
                final int totalFavour = knapsack[totalDishes][totalPaid];
                if (totalFavour == NONE) continue;

                final int totalPaidAfterService = totalPaid + getServiceCharge(totalPaid);
                if (totalPaidAfterService > maxTotalPaid) continue;

                maxTotalFavour = Math.max(maxTotalFavour, totalFavour);
            }
        }

        final double meanFavour = (double) maxTotalFavour / (double) totalPerson;
        return new Output(meanFavour);
    }

    private int getServiceCharge(final int totalPaid) {
        return totalPaid / 10 + (totalPaid % 10 == 0 ? 0 : 1);
    }
}

class Util {
    private static final String SEPARATOR = " ";

    public static String[] readSplitLine(BufferedReader in) throws IOException {
        final String line = readLine(in);
        return line.split(SEPARATOR);
    }

    public static String readLine(BufferedReader in) throws IOException {
        String line = in.readLine();
        while (line != null && line.isEmpty()) line = in.readLine();
        return line;
    }
}
