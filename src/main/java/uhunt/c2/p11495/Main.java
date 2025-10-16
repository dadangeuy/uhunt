package uhunt.c2.p11495;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * 11495 - Bubbles and Buckets
 * Time limit: 3.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=2490
 */
public class Main {
    public static void main(String... args) {
        Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 16));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 16));

        while (true) {
            int totalNumber = in.nextInt();
            if (totalNumber == 0) break;

            int[] numbers = new int[totalNumber];
            for (int i = 0; i < totalNumber; i++) numbers[i] = in.nextInt();

            Solution solution = new Solution(totalNumber, numbers);
            String winner = solution.findWinner();

            out.println(winner);
        }

        in.close();
        out.flush();
        out.close();
    }
}

/**
 * How to find the winner?
 *   If total swap is even, second player (carlos) win, else first player (marcelo) win
 * How to calculate total consecutive swap to sort the array?
 *   Use merge sort to achieve O(log(n)) sorting complexity
 *   We can calculate the number of swap needed during merge
 *   Total swap = total elements left on left-array when merging right-array
 */
class Solution {
    private static final String FIRST = "Marcelo";
    private static final String SECOND = "Carlos";
    private final int totalNumber;
    private final int[] numbers;
    private final int[] mergeNumbers;

    public Solution(int totalNumber, int[] numbers) {
        this.totalNumber = totalNumber;
        this.numbers = numbers;
        this.mergeNumbers = new int[totalNumber];
    }

    public String findWinner() {
        return findWinnerUsingMergeSort();
    }

    private String findWinnerUsingMergeSort() {
        int totalSwap = mergeSort(0, totalNumber - 1);
        return even(totalSwap) ? SECOND : FIRST;
    }

    private int mergeSort(int left, int right) {
        if (right <= left) return 0;

        int mid = (left + right) / 2;
        int swapLeft = mergeSort(left, mid);
        int swapRight = mergeSort(mid + 1, right);

        int swapMerge = 0;
        int p1 = left;
        int p2 = mid + 1;
        for (int i = left; i <= right; i++) {
            int v1 = p1 <= mid ? numbers[p1] : Integer.MAX_VALUE;
            int v2 = p2 <= right ? numbers[p2] : Integer.MAX_VALUE;

            if (v1 < v2) {
                mergeNumbers[i] = v1;
                p1++;
            } else {
                swapMerge += (mid + 1) - p1;
                mergeNumbers[i] = v2;
                p2++;
            }
        }

        System.arraycopy(mergeNumbers, left, numbers, left, right - left + 1);

        return swapLeft + swapRight + swapMerge;
    }

    private boolean even(int number) {
        return ((number & 1) == 0);
    }
}
