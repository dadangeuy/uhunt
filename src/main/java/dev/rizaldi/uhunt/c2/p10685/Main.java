package dev.rizaldi.uhunt.c2.p10685;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String... args) {
        Scanner in = new Scanner(System.in);

        while (true) {
            int totalCreature = in.nextInt();
            int totalRelation = in.nextInt();

            boolean EOF = totalCreature == 0 && totalRelation == 0;
            if (EOF) break;

            String[] names = new String[totalCreature];
            String[][] relations = new String[totalRelation][];

            for (int i = 0; i < totalCreature; i++) names[i] = in.next();
            for (int i = 0; i < totalRelation; i++) {
                String name1 = in.next();
                String name2 = in.next();
                String[] relation = new String[]{name1, name2};
                relations[i] = relation;
            }

            Solution solution = new Solution(names, relations);
            int largestChain = solution.getLargestChain();

            System.out.println(largestChain);
        }
    }
}

class Solution {
    private final String[] names;
    private final String[][] relations;
    private final Map<String, Integer> ids;

    Solution(String[] names, String[][] relations) {
        this.names = names;
        this.relations = relations;
        this.ids = new HashMap<>(2 * names.length);
    }

    public int getLargestChain() {
        int[] disjointSet = createDisjointSetFromRelations();
        return findLargestGroup(disjointSet);
    }

    private int findLargestGroup(int[] disjointSet) {
        int[] counts = new int[disjointSet.length];
        for (int id = 0; id < disjointSet.length; id++) {
            int root = getRootID(disjointSet, id);
            counts[root]++;
        }

        int maxCount = counts[0];
        for (int count : counts) maxCount = Math.max(maxCount, count);
        return maxCount;
    }

    private int[] createDisjointSetFromRelations() {
        int[] disjointSet = new int[names.length];
        Arrays.fill(disjointSet, -1);

        for (String[] relation : relations) {
            String name1 = relation[0];
            String name2 = relation[1];

            int id1 = getId(name1);
            int id2 = getId(name2);

            int root1 = getRootID(disjointSet, id1);
            int root2 = getRootID(disjointSet, id2);

            if (root1 == root2) continue;

            disjointSet[root1] = root2;
        }

        return disjointSet;
    }

    private int getId(String name) {
        return ids.computeIfAbsent(name, k -> ids.size());
    }

    private int getRootID(int[] disjointSet, int id) {
        while (disjointSet[id] != -1) id = disjointSet[id];
        return id;
    }
}
