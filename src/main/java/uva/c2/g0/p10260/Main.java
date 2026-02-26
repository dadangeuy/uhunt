package uva.c2.g0.p10260;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String... args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String line;

        while ((line = in.readLine()) != null && !line.isEmpty()) {
            Solution solution = new Solution(line);
            String soundex = solution.getSoundexCode();
            System.out.println(soundex);
        }
    }
}

// use DAT to store & retrieve soundex code in O(1)
// iterate each letter and transform the letter into soundex code using the DAT
// ignore if code doesn't exist OR duplicate (match with previous code)
// time complexity: O(n), n = length of text
// space complexity: O(m), m = total character stored in DAT (A-Z)
class Solution {
    private static final SoundexDAT soundexDat = new SoundexDAT();
    private final String text;

    public Solution(String text) {
        this.text = text;
    }

    public String getSoundexCode() {
        StringBuilder soundexCode = new StringBuilder();

        for (int i = 0; i < text.length(); i++) {
            int code = soundexDat.get(text.charAt(i));
            int prevCode = i > 0 ? soundexDat.get(text.charAt(i - 1)) : 0;

            boolean notExist = code == 0;
            boolean duplicate = code == prevCode;
            if (notExist || duplicate) continue;

            soundexCode.append(code);
        }

        return soundexCode.toString();
    }
}

// Direct Address Table that contains mapping between character to soundex code
class SoundexDAT {
    private final int[] table = new int[26];

    public SoundexDAT() {
        bulkSet(1, 'B', 'F', 'P', 'V');
        bulkSet(2, 'C', 'G', 'J', 'K', 'Q', 'S', 'X', 'Z');
        bulkSet(3, 'D', 'T');
        bulkSet(4, 'L');
        bulkSet(6, 'R');
        bulkSet(5, 'M', 'N');
    }

    public void bulkSet(int value, int... keys) {
        for (int key : keys) table[address(key)] = value;
    }

    public int get(int key) {
        return table[address(key)];
    }

    private int address(int key) {
        return key - 'A';
    }
}
