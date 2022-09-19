package dev.rizaldi.uhunt.c2.p12662;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * 12662 - Good Teacher
 * Time limit: 1.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=4400
 */
public class Main {
    public static void main(String... args) {
        Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 16));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 16));

        int totalStudent = in.nextInt();
        String[] students = new String[totalStudent];
        for (int i = 0; i < totalStudent; i++) students[i] = in.next();

        Solution solution = new Solution(totalStudent, students);

        int totalQuery = in.nextInt();
        for (int i = 0; i < totalQuery; i++) {
            int query = in.nextInt();
            String alias = solution.getAlias(query - 1);
            out.println(alias);
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Solution {
    private static final int NULL = -1;
    private final int totalStudent;
    private final String[] students;
    private final String[] aliases;

    public Solution(int totalStudent, String[] students) {
        this.totalStudent = totalStudent;
        this.students = students;
        this.aliases = getAliases();
    }

    public String getAlias(int studentId) {
        return aliases[studentId];
    }

    private String[] getAliases() {
        int prev = NULL;
        int next = findNext(0);

        String[] aliases = new String[totalStudent];
        for (int i = 0; i < totalStudent; i++) {
            boolean known = !students[i].equals("?");

            // update prev-next
            if (known) {
                prev = next;
                next = findNext(next + 1);
            }

            // update alias
            int prevDist = i - prev;
            int nextDist = next - i;
            if (prev == NULL) aliases[i] = leftOf(students[next], nextDist);
            else if (next == NULL) aliases[i] = rightOf(students[prev], prevDist);
            else if (prevDist < nextDist) aliases[i] = rightOf(students[prev], prevDist);
            else if (nextDist < prevDist) aliases[i] = leftOf(students[next], nextDist);
            else aliases[i] = middleOf(students[prev], students[next]);
        }

        return aliases;
    }


    private int findNext(int offset) {
        for (int i = offset; i < totalStudent; i++) {
            boolean known = !students[i].equals("?");
            if (known) return i;
        }
        return NULL;
    }

    private String leftOf(String name, int multiplier) {
        return multiply("left of ", multiplier) + name;
    }

    private String rightOf(String name, int multiplier) {
        return multiply("right of ", multiplier) + name;
    }

    private String middleOf(String name1, String name2) {
        return String.format("middle of %s and %s", name1, name2);
    }

    private String multiply(String word, int multiplier) {
        StringBuilder b = new StringBuilder();
        for (int i = 0; i < multiplier; i++) b.append(word);
        return b.toString();
    }
}
