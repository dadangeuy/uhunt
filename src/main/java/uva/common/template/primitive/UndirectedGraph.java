package uva.common.template.primitive;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@SuppressWarnings("unused")
final class UndirectedGraph {
    private final int[][] edges;

    private UndirectedGraph(final Builder builder) {
        edges = new int[builder.edges.length][];
        for (int vertex = 0; vertex < builder.edges.length; vertex++) {
            final List<Integer> destinations = builder.edges[vertex] == null ? Collections.emptyList() : builder.edges[vertex];
            edges[vertex] = destinations.stream().mapToInt(Integer::intValue).distinct().toArray();
        }
    }

    public int[][] get() {
        return edges;
    }

    public int[] get(final int from) {
        return edges[from];
    }

    static class Builder {
        private final LinkedList<Integer>[] edges;

        public Builder(final int maxVertex) {
            this.edges = new LinkedList[maxVertex + 1];
        }

        public void add(final int first, final int second) {
            addUni(first, second);
            addUni(second, first);
        }

        private void addUni(final int from, final int into) {
            if (edges[from] == null) edges[from] = new LinkedList<>();
            edges[from].add(into);
        }

        public UndirectedGraph build() {
            return new UndirectedGraph(this);
        }
    }
}