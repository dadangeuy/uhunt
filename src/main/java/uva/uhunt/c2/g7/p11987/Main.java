package uva.uhunt.c2.g7.p11987;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    private static BufferedReader in;
    private static String line;
    private static String[] lines;
    private static StringBuilder res;

    public static void main(String... args) throws IOException {
        in = new BufferedReader(new InputStreamReader(System.in));
        res = new StringBuilder();

        while ((line = in.readLine()) != null && !line.isEmpty()) {
            lines = line.split(" ", 2);
            int totalNumber = Integer.parseInt(lines[0]);
            int totalOperation = Integer.parseInt(lines[1]);

            DisjointSet set = new DisjointSet(totalNumber + 1);

            for (int i = 0; i < totalOperation; i++) {
                lines = in.readLine().split(" ", 3);

                int action = Integer.parseInt(lines[0]);
                int number1 = Integer.parseInt(lines[1]);

                if (action == 1) {
                    int number2 = Integer.parseInt(lines[2]);
                    set.union(number1, number2);
                } else if (action == 2) {
                    int number2 = Integer.parseInt(lines[2]);
                    set.move(number1, number2);
                } else if (action == 3) {
                    int count = set.count(number1);
                    int sum = set.sum(number1);
                    res.append(count).append(' ').append(sum).append('\n');
                }
            }
        }

        System.out.print(res);
    }
}

class DisjointSet {
    private static final int MAX = 200_001;
    private static final int[] ids = new int[MAX];
    private static final int[] parents = new int[MAX];
    private static final int[] counts = new int[MAX];
    private static final int[] sums = new int[MAX];
    private int lastId = -1;

    public DisjointSet(int size) {
        Arrays.fill(parents, 0, size, -1);
        Arrays.fill(counts, 0, size, 1);
        for (int i = 0; i < size; i++) sums[i] = i;
        for (int i = 0; i < size; i++) ids[i] = newId();
    }

    public void union(int element1, int element2) {
        int root1 = find(ids[element1]);
        int root2 = find(ids[element2]);
        if (root1 == root2) return;

        parents[root1] = root2;

        counts[root2] += counts[root1];
        counts[root1] = 0;

        sums[root2] += sums[root1];
        sums[root1] = 0;
    }

    public void move(int element1, int element2) {
        int root1 = find(ids[element1]);
        int root2 = find(ids[element2]);
        if (root1 == root2) return;

        remove(element1);
        union(element1, element2);
    }

    private void remove(int element) {
        int root = find(ids[element]);
        counts[root] -= 1;
        sums[root] -= element;

        ids[element] = newId();
        parents[ids[element]] = -1;
        counts[ids[element]] = 1;
        sums[ids[element]] = element;
    }

    public int count(int element) {
        int root = find(ids[element]);
        return counts[root];
    }

    public int sum(int element) {
        int root = find(ids[element]);
        return sums[root];
    }

    public int find(int element) {
        int root = element;
        while (parents[root] != -1) root = parents[root];
        compress(element, root);
        return root;
    }

    public void compress(int element, int root) {
        while (element != root) {
            int temp = element;
            element = parents[element];
            parents[temp] = root;
        }
    }

    private int newId() {
        return ++lastId;
    }
}
