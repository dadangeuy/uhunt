package uva.common.template.primitive;

@SuppressWarnings("unused")
final class DisjointSet {
    private final int[] parents;

    public DisjointSet(final int maxVertex) {
        parents = new int[maxVertex + 1];
        for (int vertex = 0; vertex <= maxVertex; vertex++) parents[vertex] = vertex;
    }

    public int find(final int child) {
        final int parent = parents[child];
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