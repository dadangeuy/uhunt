package uhunt.c2.g8.p10858;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * 10858 - Unique Factorization
 * Time limit: 3.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=1799
 */
public class Main {
    public static void main(String... args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder out = new StringBuilder();

        while (true) {
            int number = Integer.parseInt(in.readLine());
            if (number == 0) break;

            LinkedList<int[]> factors = new LinkedList<>();
            findCombinationOfFactors(number, new LinkedList<>(), factors);

            out.append(factors.size()).append('\n');
            for (int[] factor : factors) {
                for (int i = 0; i < factor.length - 1; i++) out.append(factor[i]).append(' ');
                out.append(factor[factor.length - 1]).append('\n');
            }
        }

        System.out.print(out);
    }

    private static void findCombinationOfFactors(
            int number,
            LinkedList<Integer> current,
            LinkedList<int[]> all
    ) {
        if (number == 1) {
            if (current.size() <= 1) return;
            all.add(current.stream().mapToInt(Integer::intValue).toArray());
            return;
        }

        for (int dividend = current.isEmpty()? 2 : current.getLast(); dividend <= number; dividend++) {
            if (number % dividend == 0) {
                current.addLast(dividend);
                findCombinationOfFactors(number / dividend, current, all);
                current.removeLast();
            }
        }
    }
}
