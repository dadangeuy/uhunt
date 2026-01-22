package uhunt.c3.g6.p386;

import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * 386 - Perfect Cubes
 * Time limit: 3.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&page=show_problem&problem=322
 */
public class Main {
    public static void main(final String... args) {
        final PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));
        final Process process = new Process();

        final Input input = new Input();
        final Output output = process.process(input);
        for (final Pair<Integer, List<Integer>> cubeAndTriple : output.listCubeAndTriple) {
            final Integer cube = cubeAndTriple.first;
            final List<Integer> triple = cubeAndTriple.second;
            out.format("Cube = %d, Triple = (%d,%d,%d)\n", cube, triple.get(0), triple.get(1), triple.get(2));
        }

        out.flush();
        out.close();
    }
}

class Input {

}

class Output {
    public final List<Pair<Integer, List<Integer>>> listCubeAndTriple;

    public Output(final List<Pair<Integer, List<Integer>>> listCubeAndTriple) {
        this.listCubeAndTriple = listCubeAndTriple;
    }
}

class Process {
    private static final int MAX_A = 200;
    private static final Comparator<Pair<Integer, List<Integer>>> ORDER_PAIR_BY_CUBE_AND_TRIPLE = Comparator
        .comparingInt((Pair<Integer, List<Integer>> p) -> p.first)
        .thenComparingInt((Pair<Integer, List<Integer>> p) -> p.second.get(0))
        .thenComparingInt((Pair<Integer, List<Integer>> p) -> p.second.get(1))
        .thenComparingInt((Pair<Integer, List<Integer>> p) -> p.second.get(2));

    public Output process(final Input input) {
        final List<Pair<Integer, List<Integer>>> listCubeAndTriple = new LinkedList<>();

        for (int b = 2; b <= MAX_A; b++) {
            for (int c = b; c <= MAX_A; c++) {
                for (int d = c; d <= MAX_A; d++) {
                    final int a3 = b * b * b + c * c * c + d * d * d;
                    final int a = (int) Math.floor(Math.cbrt(a3));

                    final boolean isValid = a <= MAX_A && a3 == a * a * a;
                    if (isValid) {
                        final List<Integer> triple = Arrays.asList(b, c, d);
                        final Pair<Integer, List<Integer>> pair = new Pair<>(a, triple);
                        listCubeAndTriple.add(pair);
                    }
                }
            }
        }

        listCubeAndTriple.sort(ORDER_PAIR_BY_CUBE_AND_TRIPLE);

        return new Output(listCubeAndTriple);
    }
}

class Pair<V1, V2> {
    public final V1 first;
    public final V2 second;

    public Pair(final V1 first, final V2 second) {
        this.first = first;
        this.second = second;
    }
}
