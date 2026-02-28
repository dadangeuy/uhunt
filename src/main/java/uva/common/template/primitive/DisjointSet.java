package uva.common.template.primitive;

import java.util.Arrays;

@SuppressWarnings("unused")
final class DisjointSet {
    private final int[] parents;

    public DisjointSet(final int maxVertex) {
        parents = new int[maxVertex + 1];
        Arrays.fill(parents, -1);
    }

    public int find(final int child) {
        final int parent = parents[child] == -1 ? child : parents[child];
        if (parent == child) {
            return parent;
        } else {
            final int grandparent = find(parent);
            parents[child] = grandparent;
            return grandparent;
        }
    }

    public void union(final int child1, final int child2) {
        final int parent1 = find(child1);
        final int parent2 = find(child2);
        parents[parent2] = parent1;
    }
}