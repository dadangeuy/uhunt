package dev.rizaldi.uhunt.p729;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String... args) {
        Scanner in = new Scanner(System.in);
        int totalTest = in.nextInt();
        for (int test = 0; test < totalTest; test++) {
            if (test > 0) System.out.println();

            int bitLength = in.nextInt();
            int hammingDistance = in.nextInt();

            Solution solution = new Solution(bitLength, hammingDistance);
            List<String> bits = solution.getAllPossibleBit();

            bits.forEach(System.out::println);
        }
    }
}

class Solution {
    private final int bitLength;
    private final int hammingDistance;
    private final List<String> possibleBits = new LinkedList<>();

    public Solution(int bitLength, int hammingDistance) {
        this.bitLength = bitLength;
        this.hammingDistance = hammingDistance;
    }

    public List<String> getAllPossibleBit() {
        if (!possibleBits.isEmpty()) return possibleBits;

        char[] initialBit = createInitialBit();
        generatePossibilities(initialBit, hammingDistance, 0, bitLength);
        return possibleBits;
    }

    private char[] createInitialBit() {
        char[] bit = new char[bitLength];
        for (int i = 0; i < bitLength; i++) bit[i] = '0';
        return bit;
    }

    private void generatePossibilities(char[] bit, int bitLeft, int from, int to) {
        if (bitLeft == 0) possibleBits.add(new String(bit));
        for (int i = to - 1; i >= from; i--) {
            bit[i] = '1';
            generatePossibilities(bit, bitLeft - 1, i + 1, to);
            bit[i] = '0';
        }
    }
}
