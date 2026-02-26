package uva.c2.g9.p1329;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String... args) {
        Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 16));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 16));

        int totalCase = in.nextInt();
        for (int i = 0; i < totalCase; i++) {
            int totalEnterprise = in.nextInt();
            Solution solution = new Solution(totalEnterprise);

            while (true) {
                char action = in.next().charAt(0);

                if (action == 'O') {
                    break;

                } else if (action == 'E') {
                    int enterprise = in.nextInt();
                    int distance = solution.distanceToServingCenter(enterprise);
                    out.println(distance);

                } else if (action == 'I') {
                    int enterprise1 = in.nextInt();
                    int enterprise2 = in.nextInt();
                    solution.linkServingCenter(enterprise1, enterprise2);

                }
            }
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Solution {
    private final int totalEnterprise;
    private final DisjointSet network;

    public Solution(int totalEnterprise) {
        this.totalEnterprise = totalEnterprise;
        this.network = new DisjointSet(totalEnterprise + 1);
    }

    public int distanceToServingCenter(int enterprise) {
        return network.distance(enterprise);
    }

    public void linkServingCenter(int enterprise1, int enterprise2) {
        network.union(enterprise1, enterprise2);
    }
}

class DisjointSet {
    private static final int ROOT = -1;
    private final int[] parents;

    public DisjointSet(int length) {
        parents = new int[length];
        Arrays.fill(parents, ROOT);
    }

    public void union(int element1, int element2) {
        int root1 = find(element1);
        int root2 = find(element2);

        if (root1 == root2) return;

        parents[root1] = element2;
    }

    public int find(int element) {
        while (parents[element] != ROOT) element = parents[element];
        return element;
    }

    public int distance(int element) {
        int total = 0;
        while (parents[element] != ROOT) {
            total += communicationLength(element, parents[element]);
            element = parents[element];
        }
        return total;
    }

    private int communicationLength(int from, int to) {
        return Math.abs(from - to) % 1_000;
    }
}
