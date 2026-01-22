package uhunt.c2.g1.p551;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 551 - Nesting a Bunch of Brackets
 * Time limit: 3.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=492
 */
public class Main {
    public static void main(String... args) {
        Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 16));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 16));

        while (in.hasNextLine()) {
            String expression = in.nextLine();

            Solution solution = new Solution(expression);
            int errorIndex = solution.getErrorIndex();

            if (errorIndex == 0) out.println("YES");
            else out.format("NO %d\n", errorIndex);
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Solution {
    private static final int NO_ERROR = 0;
    private static final Set<String> OPEN_BRACKETS = Stream.of("(", "[", "{", "<", "(*").collect(Collectors.toSet());
    private static final Set<String> CLOSE_BRACKETS = Stream.of(")", "]", "}", ">", "*)").collect(Collectors.toSet());
    private static final Set<String> BRACKETS = Stream.of("()", "[]", "{}", "<>", "(**)").collect(Collectors.toSet());
    private final String expression;

    public Solution(String expression) {
        this.expression = expression;
    }

    public int getErrorIndex() {
        LinkedList<String> openStack = new LinkedList<>();

        int idx = 1;
        for (int i = 0; i < expression.length(); ) {
            String token1 = expression.substring(i, i + 1);
            String token2 = expression.substring(i, Math.min(i + 2, expression.length()));
            String token = isBracket(token2) ? token2 : token1;

            if (OPEN_BRACKETS.contains(token)) {
                openStack.push(token);
            } else if (CLOSE_BRACKETS.contains(token)) {
                if (openStack.isEmpty()) return idx;
                String open = openStack.pop();
                if (!valid(open, token)) return idx;
            }

            i += token.length();
            idx++;
        }

        if (!openStack.isEmpty()) return idx;
        return NO_ERROR;
    }

    private boolean valid(String open, String close) {
        return BRACKETS.contains(open + close);
    }

    private boolean isBracket(String token) {
        return OPEN_BRACKETS.contains(token) || CLOSE_BRACKETS.contains(token);
    }
}
