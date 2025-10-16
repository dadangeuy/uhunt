package uhunt.c3.p165;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

/**
 * 165 - Stamps
 * Time limit: 3.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=101
 */
public class Main {
    public static void main(String... args) {
        Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 16));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 16));

        while (true) {
            int totalStamp = in.nextInt();
            int totalDenomination = in.nextInt();
            if (totalStamp == 0 && totalDenomination == 0) break;

            Solution solution = new Solution(totalStamp, totalDenomination);
            Answer answer = solution.getMaximumDenominations();

            for (int denomination : answer.denominations) out.format("%3d", denomination);
            out.format(" ->%3d\n", answer.totalConsecutiveNomination);
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Solution {
    private final int totalStamp;
    private final int totalDenomination;

    public Solution(int totalStamp, int totalDenomination) {
        this.totalStamp = totalStamp;
        this.totalDenomination = totalDenomination;
    }

    public Answer getMaximumDenominations() {
        return getMaximumDenominations(new int[totalDenomination], 0);
    }

    // get maximum denomination using dfs
    private Answer getMaximumDenominations(int[] denominations, int denominationId) {
        if (denominationId == denominations.length) {
            int total = getTotalConsecutiveNomination(denominations);
            return new Answer(denominations.clone(), total);

        } else if (denominationId == 0) {
            denominations[0] = 1;
            return getMaximumDenominations(denominations, denominationId + 1);

        } else {
            int minNomination = denominations[denominationId - 1] + 1;
            int maxNomination = getTotalConsecutiveNomination(Arrays.copyOf(denominations, denominationId)) + 1;

            Answer answer = null;
            for (int denomination = minNomination; denomination <= maxNomination; denomination++) {
                denominations[denominationId] = denomination;
                Answer otherAnswer = getMaximumDenominations(denominations, denominationId + 1);
                answer = max(answer, otherAnswer);
            }
            return answer;
        }
    }

    private Answer max(Answer first, Answer second) {
        return first == null ? second : second == null ? first : first.compareTo(second) < 0 ? second : first;
    }

    // count consecutive nomination using knapsack
    private int getTotalConsecutiveNomination(int[] denominations) {
        int lastDenomination = denominations[denominations.length - 1];
        int maxNomination = lastDenomination * totalStamp;

        int[] stampPerNomination = new int[maxNomination + 1];
        Arrays.fill(stampPerNomination, Integer.MAX_VALUE);
        stampPerNomination[0] = 0;

        for (int stamp = 1; stamp <= totalStamp; stamp++) {
            for (int denomination : denominations) {
                for (int nomination = 0; nomination <= maxNomination; nomination++) {
                    if (stampPerNomination[nomination] > totalStamp) break;
                    if (stampPerNomination[nomination] == totalStamp) continue;

                    int newNomination = nomination + denomination;
                    int newStamp = stampPerNomination[nomination] + 1;
                    stampPerNomination[newNomination] = Math.min(stampPerNomination[newNomination], newStamp);
                }
            }
        }

        int count = 0;
        for (int nomination = 1; nomination <= maxNomination; nomination++) {
            if (stampPerNomination[nomination] > totalStamp) break;
            count++;
        }
        return count;
    }
}

class Answer implements Comparable<Answer> {
    public final int[] denominations;
    public final int totalConsecutiveNomination;

    public Answer(int[] denominations, int totalConsecutiveNomination) {
        this.denominations = denominations;
        this.totalConsecutiveNomination = totalConsecutiveNomination;
    }

    @Override
    public int compareTo(Answer o) {
        return Integer.compare(totalConsecutiveNomination, o.totalConsecutiveNomination);
    }
}
