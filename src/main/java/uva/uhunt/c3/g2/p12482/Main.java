package uva.uhunt.c3.g2.p12482;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 12482 - Short Story Competition
 * Time limit: 1.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=3926
 */
public class Main {
    public static void main(final String... args) throws IOException {
        final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));
        final Process process = new Process();

        while (true) {
            final String[] l1 = readSplitLine(in), l2 = readSplitLine(in);
            final boolean isEOF = l1 == null;
            if (isEOF) break;

            final Input input = new Input();
            input.totalWords = Integer.parseInt(l1[0]);
            input.totalLinesPerPage = Integer.parseInt(l1[1]);
            input.totalCharactersPerLine = Integer.parseInt(l1[2]);
            input.words = l2;

            final Output output = process.process(input);
            out.write(Integer.toString(output.totalPages));
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
        final String line = in.readLine();
        return line == null || !line.isEmpty() ? line : readLine(in);
    }
}

class Input {
    public int totalWords;
    public int totalLinesPerPage;
    public int totalCharactersPerLine;
    public String[] words;
}

class Output {
    public int totalPages;
}

class Process {
    public Output process(final Input input) {
        final Output output = new Output();

        final List<String> words = Arrays.asList(input.words);
        final int totalCharactersPerLine = input.totalCharactersPerLine;
        final int totalLinesPerPages = input.totalLinesPerPage;

        final List<String> lines = getLines(words, totalCharactersPerLine);
        final int totalPages = ceilDiv(lines.size(), totalLinesPerPages);

        output.totalPages = totalPages;
        return output;
    }

    private List<String> getLines(final List<String> words, final int totalCharactersPerLine) {
        final LinkedList<String> lines = new LinkedList<>();
        final Queue<String> queue = new LinkedList<>(words);

        while (!queue.isEmpty()) {
            final StringBuilder line = new StringBuilder();

            while (!queue.isEmpty()) {
                final String word = queue.peek();

                // <existingText> + word + <space>
                final boolean isValid = line.length() + word.length() + 1 <= totalCharactersPerLine + 1;
                if (isValid) {
                    line.append(word).append(' ');
                    queue.remove();
                } else {
                    break;
                }
            }

            lines.add(line.toString().trim());
        }

        return lines;
    }

    private int ceilDiv(final int numerator, final int denominator) {
        final int remainder = numerator % denominator;
        final int quotient = numerator / denominator;
        return remainder == 0 ? quotient : quotient + 1;
    }
}
