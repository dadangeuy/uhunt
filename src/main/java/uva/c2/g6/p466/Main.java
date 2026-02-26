package uva.c2.g6.p466;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

enum Action {
    ROTATE_90, ROTATE_180, ROTATE_270, REFLECT_VERTICAL, PRESERVATION, IMPROPER;

    public String description() {
        switch (this) {
            case ROTATE_90:
                return "rotated 90 degrees";
            case ROTATE_180:
                return "rotated 180 degrees";
            case ROTATE_270:
                return "rotated 270 degrees";
            case REFLECT_VERTICAL:
                return "reflected vertically";
            case PRESERVATION:
                return "preserved";
            case IMPROPER:
                return "improperly transformed";
        }
        throw new RuntimeException("unknown enum");
    }
}

/**
 * 466 - Mirror, Mirror
 * Time limit: 3.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=407
 */
public class Main {
    public static void main(String... args) {
        Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 16));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 16));

        int patternId = 1;
        while (in.hasNextInt()) {
            int length = in.nextInt();
            char[][] pattern1 = new char[length][];
            char[][] pattern2 = new char[length][];

            for (int i = 0; i < length; i++) {
                pattern1[i] = in.next().toCharArray();
                pattern2[i] = in.next().toCharArray();
            }

            Solution solution = new Solution(pattern1, pattern2);
            Action[] actions = solution.getActions();
            String description = Arrays.stream(actions).map(Action::description).collect(Collectors.joining(" and "));

            out.format("Pattern %d was %s.\n", patternId++, description);
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Solution {
    private final char[][] pattern1;
    private final char[][] pattern2;

    public Solution(char[][] pattern1, char[][] pattern2) {
        this.pattern1 = pattern1;
        this.pattern2 = pattern2;
    }

    public Action[] getActions() {
        if (equals(pattern1, pattern2)) return new Action[]{Action.PRESERVATION};
        if (equals(rotate90(pattern1), pattern2)) return new Action[]{Action.ROTATE_90};
        if (equals(rotate180(pattern1), pattern2)) return new Action[]{Action.ROTATE_180};
        if (equals(rotate270(pattern1), pattern2)) return new Action[]{Action.ROTATE_270};
        if (equals(reflectVertical(pattern1), pattern2)) return new Action[]{Action.REFLECT_VERTICAL};
        if (equals(rotate90(reflectVertical(pattern1)), pattern2))
            return new Action[]{Action.REFLECT_VERTICAL, Action.ROTATE_90};
        if (equals(rotate180(reflectVertical(pattern1)), pattern2))
            return new Action[]{Action.REFLECT_VERTICAL, Action.ROTATE_180};
        if (equals(rotate270(reflectVertical(pattern1)), pattern2))
            return new Action[]{Action.REFLECT_VERTICAL, Action.ROTATE_270};
        return new Action[]{Action.IMPROPER};
    }

    private boolean equals(char[][] a1, char[][] a2) {
        for (int i = 0; i < a1.length; i++) {
            for (int j = 0; j < a1[i].length; j++) {
                if (a1[i][j] != a2[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    private char[][] rotate90(char[][] pattern) {
        int length = pattern.length;
        char[][] rotatedPattern = new char[length][length];
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                rotatedPattern[j][length - i - 1] = pattern[i][j];
            }
        }
        return rotatedPattern;
    }

    private char[][] rotate180(char[][] pattern) {
        return rotate90(rotate90(pattern));
    }

    private char[][] rotate270(char[][] pattern) {
        return rotate90(rotate90(rotate90(pattern)));
    }

    private char[][] reflectVertical(char[][] pattern) {
        int length = pattern.length;
        char[][] reflectedPattern = new char[length][length];
        for (int i = 0; i < length; i++) {
            System.arraycopy(pattern[i], 0, reflectedPattern[length - i - 1], 0, length);
        }
        return reflectedPattern;
    }
}
