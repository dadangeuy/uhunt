package uhunt.c1.p947;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {
    public static void main(String... args) {
        Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 16));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 16));

        int totalCase = in.nextInt();
        for (int i = 0; i < totalCase; i++) {
            int secret = in.nextInt();
            int totalInPlace = in.nextInt();
            int totalNotInPlace = in.nextInt();

            Solution solution = new Solution(secret, totalInPlace, totalNotInPlace);
            int total = solution.getTotalPossibleGuess();

            out.println(total);
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Solution {
    private final int[] secret;
    private final int totalInPlace;
    private final int totalNotInPlace;

    public Solution(int secret, int totalInPlace, int totalNotInPlace) {
        this.secret = toArray(secret);
        this.totalInPlace = totalInPlace;
        this.totalNotInPlace = totalNotInPlace;
    }

    public int getTotalPossibleGuess() {
        return guess(new int[secret.length], 0);
    }

    private int guess(int[] guess, int length) {
        if (length == secret.length) {
            boolean matchInPlace = countInPlace(guess, secret) == totalInPlace;
            boolean matchNotInPlace = countNotInPlace(guess, secret) == totalNotInPlace;

            boolean match = matchInPlace && matchNotInPlace;
            return match ? 1 : 0;
        }

        int total = 0;
        for (int i = 1; i <= 9; i++) {
            guess[length] = i;
            total += guess(guess, length + 1);
        }

        return total;
    }

    private int countInPlace(int[] guess, int[] secret) {
        int count = 0;
        for (int i = 0; i < guess.length; i++) count += guess[i] == secret[i] ? 1 : 0;
        return count;
    }

    private int countNotInPlace(int[] guess, int[] secret) {
        int countMatchIgnorePlace = 0;
        for (int i = 1; i <= 9; i++) {
            int countMatchGuess = countMatch(guess, i);
            int countMatchSecret = countMatch(secret, i);

            countMatchIgnorePlace += Math.min(countMatchGuess, countMatchSecret);
        }

        return countMatchIgnorePlace - countInPlace(guess, secret);
    }

    private int countMatch(int[] array, int target) {
        int count = 0;
        for (int value : array) if (value == target) count++;
        return count;
    }

    private int[] toArray(int value) {
        int total = count(value);
        int[] array = new int[total];
        for (int i = 0; i < total; i++, value /= 10) array[i] = value % 10;
        return array;
    }

    private int count(int value) {
        int count = 0;
        while (value > 0) {
            value /= 10;
            count++;
        }
        return count;
    }
}
