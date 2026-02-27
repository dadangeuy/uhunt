package uva.uhunt.c1.g6.p11736;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    public static void main(String... args) {
        Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 16));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 16));

        while (in.hasNextInt()) {
            int totalBit = in.nextInt();
            int totalVariable = in.nextInt();

            String[] variables = new String[totalVariable];
            int[] totalBytes = new int[totalVariable];
            for (int i = 0; i < totalVariable; i++) {
                variables[i] = in.next();
                totalBytes[i] = in.nextInt();
            }

            String[][] contents = new String[totalVariable][];
            for (int i = 0; i < totalVariable; i++) {
                contents[i] = new String[totalBytes[i]];
                for (int j = 0; j < totalBytes[i]; j++) {
                    contents[i][j] = in.next();
                }
            }

            Solution solution = new Solution(totalBit, totalVariable, variables, contents);

            int totalQuery = in.nextInt();
            for (int i = 0; i < totalQuery; i++) {
                String query = in.next();
                Optional<BigInteger> value = solution.getContentValue(query);
                out.format("%s=%s\n", query, value.map(v -> v.toString(10)).orElse(""));
            }
        }

        in.close();
        out.flush();
        out.close();
    }
}

/**
 * - for each variable, build full content by appending contents
 * - convert full content to big integer
 * - store in hashmap to speed up search
 * - return as optional
 * - why big integer? max total bit for variable is 64, java's long is 64-bit signed
 */
class Solution {
    private final int totalBit;
    private final int totalVariable;
    private final String[] variables;
    private final String[][] contents;
    private final Map<String, BigInteger> valuePerVariable;

    public Solution(int totalBit, int totalVariable, String[] variables, String[][] contents) {
        this.totalBit = totalBit;
        this.totalVariable = totalVariable;
        this.variables = variables;
        this.contents = contents;
        this.valuePerVariable = indexValuePerVariable();
    }

    private Map<String, BigInteger> indexValuePerVariable() {
        Map<String, BigInteger> index = new HashMap<>(2 * totalVariable);
        for (int i = 0; i < totalVariable; i++) {
            String variable = variables[i];
            String fullContent = append(contents[i]);
            BigInteger contentValue = new BigInteger(fullContent, 2);
            index.put(variable, contentValue);
        }
        return index;
    }

    private String append(String... values) {
        StringBuilder sb = new StringBuilder();
        for (String value : values) sb.append(value);
        return sb.toString();
    }

    public Optional<BigInteger> getContentValue(String variable) {
        return Optional.ofNullable(valuePerVariable.get(variable));
    }
}
