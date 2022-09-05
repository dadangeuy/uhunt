package dev.rizaldi.uhunt.c2.p11111;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * 11111 - Generalized Matrioshkas
 * Time limit: 3.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=2052
 */
public class Main {
    public static void main(String... args) {
        Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 16));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 16));

        while (in.hasNextInt()) {
            String line = in.nextLine();

            int[] toys = Arrays.stream(line.split("( )+"))
                    .filter(s -> !s.isEmpty())
                    .mapToInt(Integer::parseInt).toArray();

            Solution solution = new Solution(toys);
            boolean goodDesign = solution.isGoodDesign();

            out.println(goodDesign ? ":-) Matrioshka!" : ":-( Try again.");
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Solution {
    private final int[] toys;

    public Solution(int[] toys) {
        this.toys = toys;
    }

    public boolean isGoodDesign() {
        LinkedList<Integer> toyStack = new LinkedList<>();
        for (int toy : toys) {
            if (positive(toy)) {
                int nestedToy = 0;
                while (!toyStack.isEmpty() && toyStack.peek() > 0) {
                    nestedToy += toyStack.pop();
                    if (nestedToy >= toy) return false;
                }
                if (toyStack.isEmpty()) return false;
                if (toyStack.pop() != -toy) return false;
                toyStack.push(toy);
            } else {
                toyStack.push(toy);
            }
        }

        return allPositive(toyStack);
    }

    private boolean allPositive(Collection<Integer> items) {
        return items.stream().allMatch(this::positive);
    }

    private boolean positive(int i) {
        return i > 0;
    }
}
