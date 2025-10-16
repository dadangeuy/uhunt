package uhunt.c3.p11832;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 11832 - Account Book
 * Time limit: 5.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=2932
 */
public class Main {
    public static void main(final String... args) throws IOException {
        final BufferedReader in = new BufferedReader(new InputStreamReader(System.in), 1 << 16);
        final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out), 1 << 16);
        final Process process = new Process();

        while (true) {
            final String[] l1 = readSplitLine(in);
            final int totalTransactions = Integer.parseInt(l1[0]);
            final int cashflow = Integer.parseInt(l1[1]);

            final boolean isEOF = totalTransactions == 0 && cashflow == 0;
            if (isEOF) break;

            final int[] transactions = new int[totalTransactions];
            for (int i = 0; i < totalTransactions; i++) {
                final String l2 = readLine(in);
                final int transaction = Integer.parseInt(l2);
                transactions[i] = transaction;
            }

            final Input input = new Input(totalTransactions, cashflow, transactions);
            final Output output = process.process(input);

            out.write(output.transactionTypeList);
            out.write('\n');
        }

        in.close();
        out.flush();
        out.close();
    }

    private static String[] readSplitLine(final BufferedReader in) throws IOException {
        final String line = readLine(in);
        return line == null ? null : line.split(" ");
    }

    private static String readLine(final BufferedReader in) throws IOException {
        String line = in.readLine();
        while (line != null && line.isEmpty()) line = in.readLine();
        return line;
    }
}

class Input {
    public final int totalTransactions;
    public final int cashflow;
    public final int[] transactions;

    public Input(final int totalTransactions, final int cashflow, final int[] transactions) {
        this.totalTransactions = totalTransactions;
        this.cashflow = cashflow;
        this.transactions = transactions;
    }
}

class Output {
    public final char[] transactionTypeList;

    public Output(final char[] transactionTypeList) {
        this.transactionTypeList = transactionTypeList;
    }
}

class Process {
    private static final char[] IMPOSSIBLE_STATE = new char[]{TransactionType.IMPOSSIBLE};

    public Output process(final Input input) {
        // store subset sum and state
        final Map<Integer, char[]> knapsack1 = new HashMap<>();
        final Map<Integer, char[]> knapsack2 = new HashMap<>();
        final Map<Integer, char[]> knapsack3 = new HashMap<>();

        knapsack1.put(0, new char[0]);

        for (int i = 0; i < input.totalTransactions; i++) {
            final int transaction = input.transactions[i];

            // for each existing sum, increment by current transaction value
            knapsack2.clear();
            for (Map.Entry<Integer, char[]> entry : knapsack1.entrySet()) {
                final int sum = entry.getKey();
                final char[] state = entry.getValue();
                final int newSum = sum + transaction;
                final char[] newState = append(state, TransactionType.POSITIVE);

                knapsack2.put(newSum, newState);
            }

            // for each existing sum, decrement by current transaction value
            knapsack3.clear();
            for (Map.Entry<Integer, char[]> entry : knapsack1.entrySet()) {
                final int sum = entry.getKey();
                final char[] state = entry.getValue();
                final int newSum = sum - transaction;
                final char[] newState = append(state, TransactionType.NEGATIVE);

                knapsack3.put(newSum, newState);
            }

            // merge knapsack2 and knapsack3 into knapsack1, find conflict as we go
            knapsack1.clear();
            for (Map<Integer, char[]> otherKnapsack : Arrays.asList(knapsack2, knapsack3)) {
                for (Map.Entry<Integer, char[]> entry : otherKnapsack.entrySet()) {
                    final int sum = entry.getKey();
                    final char[] state = entry.getValue();

                    if (knapsack1.containsKey(sum)) {
                        final char[] existingState = knapsack1.get(sum);
                        final char[] mergeState = mergeState(state, existingState);
                        knapsack1.put(sum, mergeState);
                    } else {
                        knapsack1.put(sum, state);
                    }
                }
            }
        }

        final char[] transactionTypeList = knapsack1.getOrDefault(input.cashflow, IMPOSSIBLE_STATE);
        return new Output(transactionTypeList);
    }

    private char[] mergeState(final char[] state1, final char[] state2) {
        final char[] merged = new char[Math.max(state1.length, state2.length)];
        for (int i = 0; i < merged.length; i++) {
            final char type1 = i < state1.length ? state1[i] : state2[i];
            final char type2 = i < state2.length ? state2[i] : state1[i];
            final char transactionType = type1 == type2 ? type1 : TransactionType.BOTH;
            merged[i] = transactionType;
        }
        return merged;
    }

    private char[] append(final char[] array, final char value) {
        final char[] appended = Arrays.copyOf(array, array.length + 1);
        appended[appended.length - 1] = value;
        return appended;
    }
}

class TransactionType {
    public static final char POSITIVE = '+';
    public static final char NEGATIVE = '-';
    public static final char BOTH = '?';
    public static final char IMPOSSIBLE = '*';
}
