package uva.c1.g5.p12545;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

public class Main {
    public static void main(String... args) {
        Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 16));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 16));

        int totalCase = in.nextInt();
        for (int i = 0; i < totalCase; i++) {
            char[] bits1 = in.next().toCharArray();
            char[] bits2 = in.next().toCharArray();

            Solution solution = new Solution(bits1, bits2);
            int total = solution.getTotalMoveForTransformation();

            out.format("Case %d: %d\n", i + 1, total);
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Solution {
    private final char[] bits1;
    private final char[] bits2;

    public Solution(char[] bits1, char[] bits2) {
        this.bits1 = bits1;
        this.bits2 = bits2;
    }

    public int getTotalMoveForTransformation() {
        int totalMove = 0;
        totalMove += swap0And1();
        totalMove += swap1AndQ();
        totalMove += flip0To1();
        totalMove += assignQ();

        boolean match = Arrays.equals(bits1, bits2);
        return match ? totalMove : -1;
    }

    private int swap0And1() {
        LinkedList<Integer> missmatch0 = new LinkedList<>();
        LinkedList<Integer> missmatch1 = new LinkedList<>();
        for (int i = 0; i < bits1.length; i++) {
            if (bits1[i] == bits2[i]) continue;
            if (bits1[i] == '0') missmatch0.addLast(i);
            if (bits1[i] == '1') missmatch1.addLast(i);
        }

        int totalMove = Math.min(missmatch0.size(), missmatch1.size());
        while (!missmatch0.isEmpty() && !missmatch1.isEmpty()) {
            swap(bits1, missmatch0.removeLast(), missmatch1.removeLast());
        }

        return totalMove;
    }

    private int swap1AndQ() {
        LinkedList<Integer> missmatch1 = new LinkedList<>();
        LinkedList<Integer> missmatchQ = new LinkedList<>();
        for (int i = 0; i < bits1.length; i++) {
            if (bits1[i] == bits2[i]) continue;
            if (bits1[i] == '1') missmatch1.addLast(i);
            if (bits1[i] == '?' && bits2[i] == '1') missmatchQ.addLast(i);
        }

        int totalMove = Math.min(missmatch1.size(), missmatchQ.size());
        while (!missmatch1.isEmpty() && !missmatchQ.isEmpty()) {
            swap(bits1, missmatch1.removeLast(), missmatchQ.removeLast());
        }

        return totalMove;
    }

    private void swap(char[] arr, int i, int j) {
        char tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    private int flip0To1() {
        int totalMove = 0;
        for (int i = 0; i < bits1.length; i++) {
            if (bits1[i] == bits2[i]) continue;
            if (bits1[i] == '0') {
                bits1[i] = '1';
                totalMove++;
            }
        }
        return totalMove;
    }

    private int assignQ() {
        int totalMove = 0;
        for (int i = 0; i < bits1.length; i++) {
            if (bits1[i] == bits2[i]) continue;
            if (bits1[i] == '?') {
                bits1[i] = bits2[i];
                totalMove++;
            }
        }
        return totalMove;
    }
}
