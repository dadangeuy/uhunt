package uva.uhunt.c2.g7.p727;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String... args) {
        Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 16));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 16));

        int totalCase = in.nextInt();
        in.nextLine();
        in.nextLine();

        for (int i = 0; i < totalCase; i++) {
            List<String> tokens = new LinkedList<>();

            while (in.hasNextLine()) {
                String token = in.nextLine();
                if (token.isEmpty()) break;
                tokens.add(token);
            }

            Solution solution = new Solution(tokens);
            String expression = solution.getPostfixExpression();

            out.println(expression);
            if (i < totalCase - 1) out.println();
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Solution {
    private static final List<String> operands = Arrays.asList("+", "-", "*", "/");
    private final List<String> tokens;

    public Solution(List<String> tokens) {
        this.tokens = tokens;
    }

    public String getPostfixExpression() {
        LinkedList<String> stack = new LinkedList<>();
        StringBuilder postfix = new StringBuilder(tokens.size());

        for (String token : tokens) {
            if (operands.contains(token)) {
                while (!stack.isEmpty() &&
                       !stack.getFirst().equals("(") &&
                       priority(token) <= priority(stack.getFirst())
                ) postfix.append(stack.removeFirst());
                stack.addFirst(token);

            } else if (token.equals("(")) {
                stack.addFirst(token);

            } else if (token.equals(")")) {
                while (!stack.getFirst().equals("(")) postfix.append(stack.removeFirst());
                stack.removeFirst();

            } else {
                postfix.append(token);
            }
        }

        while (!stack.isEmpty()) postfix.append(stack.removeFirst());

        return postfix.toString();
    }

    private int priority(String token) {
        if (token.equals("*") || token.equals("/")) return 1;
        else return 0;
    }
}
