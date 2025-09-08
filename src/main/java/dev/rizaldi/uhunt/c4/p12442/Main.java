package dev.rizaldi.uhunt.c4.p12442;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashSet;
import java.util.Set;

/**
 * 12442 - Forwarding Emails
 * Time limit: 1.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=3873#google_vignette
 * THIS PROBLEM HAS A STRICT TIME LIMIT, CAUSING INTERMITTENT TLE ON JAVA
 */
public class Main {
    public static void main(final String... args) throws IOException {
        final BufferedReader in = new BufferedReader(new InputStreamReader(System.in), 1 << 16);
        final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out), 1 << 16);
        final Process process = new Process();

        final int totalCases = Integer.parseInt(Util.readLine(in));
        for (int caseId = 1; caseId <= totalCases; caseId++) {
            final int totalMartians = Integer.parseInt(Util.readLine(in));
            final Email[] emails = new Email[totalMartians];
            for (int forwardId = 0; forwardId < totalMartians; forwardId++) {
                final String[] l1 = Util.splitReadLine(in);
                final Email email = new Email(Integer.parseInt(l1[0]), Integer.parseInt(l1[1]));
                emails[forwardId] = email;
            }

            final Input input = new Input(caseId, totalMartians, emails);
            final Output output = process.process(input);

            out.write(String.format("Case %d: %d\n", output.caseId, output.firstReceiver));
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Email {
    public final int forwarder;
    public final int receiver;

    public Email(final int forwarder, final int receiver) {
        this.forwarder = forwarder;
        this.receiver = receiver;
    }
}

class Process {
    public Output process(final Input input) {
        final int[] receivers = new int[input.totalMartians + 1];
        for (final Email email : input.emails) {
            receivers[email.forwarder] = email.receiver;
        }

        final Set<Integer> unmailedMartians = findUnmailedMartians(receivers);
        final Set<Integer> circularMartians = findCircularMartians(receivers);

        final int[] totals = new int[input.totalMartians + 1];

        // find total for circular martians
        for (int circularMartian : circularMartians) {
            int current = circularMartian;

            int total = 0;
            do {
                total++;
                current = receivers[current];
            } while (current != circularMartian);

            do {
                totals[current] = total;
                current = receivers[current];
            } while (current != circularMartian);
        }

        // find total for linear martians
        for (int unmailedMartian : unmailedMartians) {
            int current = unmailedMartian;

            int prevTotal = 0;
            do {
                prevTotal++;
                current = receivers[current];
            } while (totals[current] == 0);
            int total = prevTotal + totals[current];
            totals[unmailedMartian] = total;
        }

        int maxTotal = 0;
        int maxFirst = 0;
        for (int martian = 1; martian <= input.totalMartians; martian++) {
            int total = totals[martian];
            if (total > maxTotal) {
                maxTotal = total;
                maxFirst = martian;
            }
        }

        return new Output(input.caseId, maxFirst);
    }

    // O(totalMartian) = 5 * 10^4
    private Set<Integer> findCircularMartians(final int[] receivers) {
        final Set<Integer> circular = new HashSet<>();

        final boolean[] visited = new boolean[receivers.length];
        final Set<Integer> path = new HashSet<>();

        for (int martian = 1; martian < receivers.length; martian++) {
            path.clear();

            int current = martian;
            while (!visited[current]) {
                path.add(current);
                visited[current] = true;
                current = receivers[current];
            }

            int next = receivers[current];
            if (path.contains(next)) circular.add(next);
        }

        return circular;
    }

    // O(totalMartians) = 5 * 10^4
    private Set<Integer> findUnmailedMartians(final int[] receivers) {
        final boolean[] isEmailed = new boolean[receivers.length];
        for (int martian = 1; martian < receivers.length; martian++) {
            isEmailed[receivers[martian]] = true;
        }

        final Set<Integer> unmailedMartians = new HashSet<>();
        for (int martian = 1; martian < receivers.length; martian++) {
            if (!isEmailed[martian]) {
                unmailedMartians.add(martian);
            }
        }

        return unmailedMartians;
    }
}

class Input {
    public final int caseId;
    public final int totalMartians;
    public final Email[] emails;

    Input(final int caseId, final int totalMartians, final Email[] emails) {
        this.caseId = caseId;
        this.totalMartians = totalMartians;
        this.emails = emails;
    }
}

class Output {
    public final int caseId;
    public final int firstReceiver;

    public Output(final int caseId, final int firstReceiver) {
        this.caseId = caseId;
        this.firstReceiver = firstReceiver;
    }
}

class Util {
    private static final String SEPARATOR = " ";

    public static String[] splitReadLine(final BufferedReader in) throws IOException {
        final String line = readLine(in);
        return line == null ? new String[0] : line.split(SEPARATOR);
    }

    public static String readLine(final BufferedReader in) throws IOException {
        String line = in.readLine();
        while (line != null && line.isEmpty()) line = in.readLine();
        return line;
    }
}
