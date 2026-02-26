package uva.c1.g7.p397;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 397 - Equation Elation
 * Time limit: 3.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=333Z
 */
public class Main {
    public static void main(String... args) {
        Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 16));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 16));

        while (in.hasNextLine()) {
            String equation = in.nextLine();

            Solution solution = new Solution(equation);
            List<String> steps = solution.getSteps();

            for (String step : steps) out.println(step);
            if (in.hasNextLine()) out.println();
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Solution {
    private final String equation;

    public Solution(String equation) {
        this.equation = equation;
    }

    public List<String> getSteps() {
        List<Integer> decimals = getDecimals();
        List<String> operators = getOperators();
        String variable = getVariable();

        mergeSign(decimals, operators);

        List<String> steps = new ArrayList<>(decimals.size());
        steps.add(toStep(decimals, operators, variable));

        while (operators.size() > 1) {
            int firstMultiply = first(operators, "*");
            int firstDivisor = first(operators, "/");
            int firstPlus = first(operators, "+");
            int firstMinus = first(operators, "-");

            if (firstMultiply != operators.size() || firstDivisor != operators.size()) {
                int operatorId = Math.min(firstMultiply, firstDivisor);

                int decimal1 = decimals.remove(operatorId);
                String operator = operators.remove(operatorId);
                int decimal2 = decimals.remove(operatorId);

                boolean invalid = operator.equals("/") && decimal1 % decimal2 != 0;
                if (invalid) {
                    steps.add("Invalid input");
                    break;
                }

                int resut = operator.equals("*") ? decimal1 * decimal2 : decimal1 / decimal2;
                decimals.add(operatorId, resut);
            } else if (firstPlus != operators.size() || firstMinus != operators.size()) {
                int operatorId = Math.min(firstPlus, firstMinus);

                int decimal1 = decimals.remove(operatorId);
                String operator = operators.remove(operatorId);
                int decimal2 = decimals.remove(operatorId);

                int result = operator.equals("+") ? decimal1 + decimal2 : decimal1 - decimal2;
                decimals.add(operatorId, result);
            }

            String step = toStep(decimals, operators, variable);
            steps.add(step);
        }

        return steps;
    }

    private List<Integer> getDecimals() {
        List<Integer> decimals = new ArrayList<>();

        Pattern pattern = Pattern.compile("[0-9]+");
        Matcher matcher = pattern.matcher(equation);
        while (matcher.find()) {
            String group = matcher.group();
            decimals.add(Integer.parseInt(group));
        }

        return decimals;
    }

    private List<String> getOperators() {
        List<String> operators = new ArrayList<>();

        Pattern pattern = Pattern.compile("[ =*/+-]+");
        Matcher matcher = pattern.matcher(equation);
        while (matcher.find()) {
            String group = matcher.group();
            String cleanGroup = group.replaceAll(" ", "");
            operators.add(cleanGroup);
        }

        return operators;
    }

    private String getVariable() {
        Pattern pattern = Pattern.compile("[a-zA-Z]+");
        Matcher matcher = pattern.matcher(equation);
        return matcher.find() ? matcher.group() : "";
    }

    private void mergeSign(List<Integer> decimals, List<String> operators) {
        for (int i = 0; i < operators.size(); i++) {
            String completeOperator = operators.get(i);
            if (completeOperator.length() == 1) continue;

            String operator = completeOperator.substring(0, 1);
            String sign = completeOperator.substring(completeOperator.length() - 1);

            operators.set(i, operator);
            if (sign.equals("-")) decimals.set(i + 1, -decimals.get(i + 1));
        }
    }

    private String toStep(List<Integer> decimals, List<String> operators, String variable) {
        StringBuilder step = new StringBuilder();

        step.append(decimals.get(0));
        for (int i = 0; i < operators.size() - 1; i++) {
            step.append(' ').append(operators.get(i)).append(' ').append(decimals.get(i + 1));
        }
        step.append(" = ").append(variable);

        return step.toString();
    }

    private int first(List<String> values, String target) {
        for (int i = 0; i < values.size(); i++) if (values.get(i).equals(target)) return i;
        return values.size();
    }
}
