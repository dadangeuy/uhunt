package uhunt.c2.p11348;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * 11348 - Exhibition
 * Time limit: 1.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=2323
 */
public class Main {
    public static void main(String... args) {
        Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 16));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 16));

        int totalTest = in.nextInt();
        for (int i = 0; i < totalTest; i++) {
            int totalFriend = in.nextInt();
            int[][] stampsPerFriend = new int[totalFriend][];
            for (int j = 0; j < totalFriend; j++) {
                int totalStamp = in.nextInt();
                int[] stamps = new int[totalStamp];
                for (int k = 0; k < totalStamp; k++) {
                    int stamp = in.nextInt();
                    stamps[k] = stamp;
                }
                stampsPerFriend[j] = stamps;
            }

            Solution solution = new Solution(totalFriend, stampsPerFriend);
            double[] incomes = solution.getIncomePercentages();

            String formattedIncomes = Arrays.stream(incomes)
                    .mapToObj(r -> String.format("%.6f%%", r))
                    .collect(Collectors.joining(" "));
            out.format("Case %d: %s\n", i + 1, formattedIncomes);
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Solution {
    private final int totalFriend;
    private final int[][] stampsPerFriend;
    private final int[][] uniqueStampsPerFriend;

    public Solution(int totalFriend, int[][] stampsPerFriend) {
        this.totalFriend = totalFriend;
        this.stampsPerFriend = stampsPerFriend;
        this.uniqueStampsPerFriend = getUniqueStampsPerFriends();
    }

    public double[] getIncomePercentages() {
        Map<Integer, Integer> countPerStamp = getCountPerStamp();

        int[] incomes = new int[totalFriend];
        for (int friend = 0; friend < totalFriend; friend++) {
            for (int stamp : uniqueStampsPerFriend[friend]) {
                boolean unique = countPerStamp.get(stamp) == 1;
                if (unique) incomes[friend]++;
            }
        }
        int total = Arrays.stream(incomes).sum();
        if (total == 0) return new double[0];

        return Arrays.stream(incomes).mapToDouble(i -> i * 100).map(i -> i / total).toArray();
    }

    private int[][] getUniqueStampsPerFriends() {
        int[][] uniqueStampsPerFriends = new int[totalFriend][];
        for (int friend = 0; friend < totalFriend; friend++) {
            uniqueStampsPerFriends[friend] = Arrays.stream(stampsPerFriend[friend]).distinct().toArray();
        }
        return uniqueStampsPerFriends;
    }

    private Map<Integer, Integer> getCountPerStamp() {
        Map<Integer, Integer> counts = new HashMap<>();
        for (int[] stamps : uniqueStampsPerFriend) {
            for (int stamp : stamps) {
                counts.putIfAbsent(stamp, 0);
                counts.computeIfPresent(stamp, (k, v) -> v + 1);
            }
        }
        return counts;
    }
}
