package uhunt.c2.p11222;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.IntStream;

/**
 * 11222 - Only I did it!
 * Time limit: 3.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=2163
 */
public class Main {
    public static void main(String... args) {
        Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 16));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 16));

        int totalCase = in.nextInt();
        for (int i = 0; i < totalCase; i++) {
            int[] firstProblems = nextInts(in, in.nextInt());
            int[] secondProblems = nextInts(in, in.nextInt());
            int[] thirdProblems = nextInts(in, in.nextInt());
            int[][] problemsPerSolver = new int[][]{firstProblems, secondProblems, thirdProblems};

            Solution solution = new Solution(problemsPerSolver);
            int[] solvers = solution.getTopSolvers();
            out.format("Case #%d:\n", i + 1);
            for (int solver : solvers) {
                int[] problems = solution.getUniqueProblems(solver);
                out.format("%d %d", solver + 1, problems.length);
                for (int problem : problems) out.format(" %d", problem);
                out.println();
            }
        }

        in.close();
        out.flush();
        out.close();
    }

    private static int[] nextInts(Scanner in, int total) {
        int[] ints = new int[total];
        for (int i = 0; i < total; i++) ints[i] = in.nextInt();
        return ints;
    }
}

class Solution {
    private static final int MAX_PROBLEM = 10_000;
    private static final int TOTAL_SOLVER = 3;
    private final int[][] problemsPerSolver;
    private int[] _bitmaskSolversPerProblem;
    private int[][] _uniqueProblemsPerSolver;

    public Solution(int[][] problemsPerSolver) {
        this.problemsPerSolver = problemsPerSolver;
    }

    /**
     * return list of [solver, totalUniqueProblems, uniqueProblems...] that has the most unique problems
     */
    public int[] getTopSolvers() {
        int[][] problemsPerSolver = getUniqueProblemsPerSolver();
        int maxProblems = Arrays.stream(problemsPerSolver).mapToInt(s -> s.length).max().orElse(0);
        return IntStream.range(0, TOTAL_SOLVER)
                .filter(s -> problemsPerSolver[s].length == maxProblems)
                .toArray();
    }

    private int[][] getUniqueProblemsPerSolver() {
        if (_uniqueProblemsPerSolver != null) return _uniqueProblemsPerSolver;

        int[][] uniqueProblemsPerSolver = IntStream.range(0, TOTAL_SOLVER)
                .mapToObj(this::getUniqueProblems)
                .toArray(int[][]::new);
        return _uniqueProblemsPerSolver = uniqueProblemsPerSolver;
    }

    public int[] getUniqueProblems(int solver) {
        if (_uniqueProblemsPerSolver != null) return _uniqueProblemsPerSolver[solver];

        int bitSolver = (1 << solver);
        int[] solversPerProblem = getBitmaskSolversPerProblem();
        return IntStream.rangeClosed(0, MAX_PROBLEM)
                .filter(p -> solversPerProblem[p] == bitSolver)
                .sorted()
                .toArray();
    }

    private int[] getBitmaskSolversPerProblem() {
        if (_bitmaskSolversPerProblem != null) return _bitmaskSolversPerProblem;

        int[] solversPerProblem = new int[MAX_PROBLEM + 1];
        for (int solver = 0; solver < TOTAL_SOLVER; solver++) {
            int bitmaskSolver = (1 << solver);
            for (int problem : problemsPerSolver[solver]) {
                solversPerProblem[problem] |= bitmaskSolver;
            }
        }
        return _bitmaskSolversPerProblem = solversPerProblem;
    }
}
