package uva.uhunt.c1.g6.p10906;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * 10906 - Strange Integration
 * Time limit: 3.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=1847
 */
public class Main {
    public static void main(String... args) {
        Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 16));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 16));

        int totalCase = in.nextInt();
        for (int i = 0; i < totalCase; i++) {
            int totalExpression = in.nextInt();
            in.nextLine();
            String[] expressions = new String[totalExpression];
            for (int j = 0; j < totalExpression; j++) expressions[j] = in.nextLine();

            Solution solution = new Solution(totalExpression, expressions);
            String evaluatedExpression = solution.evaluate();

            out.format("Expression #%d: %s\n", i + 1, evaluatedExpression);
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Solution {
    private final int totalExpression;
    private final String[] expressions;

    public Solution(int totalExpression, String[] expressions) {
        this.totalExpression = totalExpression;
        this.expressions = expressions;
    }

    public String evaluate() {
        Map<String, String> expressionPerVariable = new HashMap<>();
        for (String expression : expressions) {
            String[] e = expression.split(" ");
            expressionPerVariable.put(e[0], e[2] + " " + e[3] + " " + e[4]);
        }

        String lastVariable = expressions[totalExpression - 1].split(" ", 2)[0];
        return doEvaluate(expressionPerVariable, lastVariable);
    }

    private String doEvaluate(Map<String, String> expressionPerVariable, String variable) {
        String expression = expressionPerVariable.get(variable);

        String[] e = expression.split(" ");
        String v1 = e[0], op = e[1], v2 = e[2];

        String e1 = variable(v1) ? doEvaluate(expressionPerVariable, v1) : v1;
        String e2 = variable(v2) ? doEvaluate(expressionPerVariable, v2) : v2;

        String opc1 = variable(v1) ? expressionPerVariable.get(v1).split(" ")[1] : "n";
        String opc2 = variable(v2) ? expressionPerVariable.get(v2).split(" ")[1] : "n";

        String format = opc1 + op + opc2;
        switch (format) {
            case "*++":
            case "**+":
            case "***":
            case "n++":
            case "+++":
            case "n**":
            case "n*+":
                return e1 + op + bracket(e2);
            case "+**":
            case "+*+":
                return bracket(e1) + op + bracket(e2);
            case "+*n":
                return bracket(e1) + op + e2;
        }

        return e1 + op + e2;
    }

    private boolean variable(String value) {
        return !number(value);
    }

    private boolean number(String value) {
        return between('0', value.charAt(0), '9');
    }

    private boolean between(char left, char mid, char right) {
        return left <= mid && mid <= right;
    }

    private String bracket(String value) {
        return String.format("(%s)", value);
    }
}
