package uva.uhunt.c3.g0.p10340;

import java.util.Scanner;

public class Main {
    public static void main(String... args) {
        Scanner in = new Scanner(System.in);
        while (in.hasNext()) {
            String original = in.next();
            String encoded = in.next();

            Solution solution = new Solution(original, encoded);
            boolean subsequence = solution.isSubsequence();

            System.out.println(subsequence? "Yes" : "No");
        }
    }
}

class Solution {
    private final String original;
    private final String encoded;

    public Solution(String original, String encoded) {
        this.original = original;
        this.encoded = encoded;
    }

    public boolean isSubsequence() {
        int length = 0;
        for (int i = 0; i < encoded.length() && length < original.length(); i++) {
            char originalLetter = original.charAt(length);
            char encodedLetter = encoded.charAt(i);
            if (originalLetter == encodedLetter) length++;
        }

        return length == original.length();
    }
}
