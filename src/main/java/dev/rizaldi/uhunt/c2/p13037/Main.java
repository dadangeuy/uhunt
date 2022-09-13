package dev.rizaldi.uhunt.c2.p13037;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * 13037 - Chocolate
 * Time limit: 2.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=4935
 */
public class Main {
    public static void main(String... args) {
        Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 16));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 16));

        int totalCase = in.nextInt();
        for (int i = 0; i < totalCase; i++) {
            int total1 = in.nextInt();
            int total2 = in.nextInt();
            int total3 = in.nextInt();

            int[] lejuItems = new int[total1], ronyItems = new int[total2], sujonItems = new int[total3];
            for (int j = 0; j < total1; j++) lejuItems[j] = in.nextInt();
            for (int j = 0; j < total2; j++) ronyItems[j] = in.nextInt();
            for (int j = 0; j < total3; j++) sujonItems[j] = in.nextInt();

            Solution solution = new Solution(lejuItems, ronyItems, sujonItems);
            int[][] totalUniqueAndMissingPerPerson = solution.getTotalUniqueAndMissingPerPerson();

            out.format("Case #%d:\n", i + 1);
            for (int[] totalUniqueAndMissing : totalUniqueAndMissingPerPerson) {
                out.format("%d %d\n", totalUniqueAndMissing[0], totalUniqueAndMissing[1]);
            }
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Solution {
    private static final int TOTAL_PERSON = 3;
    private final int[][] itemsPerPerson;
    private final Map<Integer, Integer> ownersPerItem;

    public Solution(int[] lejuItems, int[] ronyItems, int[] sujonItems) {
        this.itemsPerPerson = new int[][]{lejuItems, ronyItems, sujonItems};
        this.ownersPerItem = getOwnersPerItem();
    }

    public int[][] getTotalUniqueAndMissingPerPerson() {
        int[][] totalUniqueAndMissingPerPerson = new int[3][];
        for (int person = 0; person < 3; person++) {
            int totalUnique = getTotalUnique(person);
            int totalMissing = getTotalMissing(person);
            totalUniqueAndMissingPerPerson[person] = new int[]{totalUnique, totalMissing};
        }
        return totalUniqueAndMissingPerPerson;
    }

    private Map<Integer, Integer> getOwnersPerItem() {
        Map<Integer, Integer> ownersPerItem = new HashMap<>();
        for (int person = 0; person < 3; person++) {
            int personBit = (1 << person);
            for (int item : itemsPerPerson[person]) {
                ownersPerItem.putIfAbsent(item, 0);
                ownersPerItem.put(item, ownersPerItem.get(item) | personBit);
            }
        }
        return ownersPerItem;
    }

    private int getTotalUnique(int person) {
        int total = 0;
        int personBit = (1 << person);
        for (int state : ownersPerItem.values()) {
            boolean soleOwner = state == personBit;
            if (soleOwner) total++;
        }
        return total;
    }

    private int getTotalMissing(int person) {
        int total = 0;
        int personBit = (1 << person);
        int allPersonBit = (1 << TOTAL_PERSON) - 1;
        int otherPersonBit = ~personBit & allPersonBit;
        for (int state : ownersPerItem.values()) {
            boolean ownedByOthers = state == otherPersonBit;
            if (ownedByOthers) total++;
        }
        return total;
    }
}
