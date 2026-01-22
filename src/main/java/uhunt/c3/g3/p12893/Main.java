package uhunt.c3.g3.p12893;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * 12893 - Count It
 * Time limit: 1.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&page=show_problem&problem=4758
 */
public class Main {
    public static void main(final String... args) throws IOException {
        final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));
        final Process process = new Process();

        final int totalCases = Integer.parseInt(readLine(in));
        for (int i = 0; i < totalCases; i++) {
            final Input input = new Input();
            input.index = Long.parseLong(readLine(in));

            final Output output = process.process(input);
            out.write(Long.toString(output.value));
            out.write('\n');
        }

        in.close();
        out.flush();
        out.close();
    }

    private static String readLine(final BufferedReader in) throws IOException {
        final String line = in.readLine();
        final boolean isValid = line == null || !line.isEmpty();
        return isValid ? line : readLine(in);
    }
}

class Input {
    public long index;
}

class Output {
    public long value;
}

class Process {
    public Output process(final Input input) {
        final Output output = new Output();
        output.value = getValue(input.index);
        return output;
    }

    public long getValue(long index) {
        long count = 0;
        while (index > 0) {
            count += index & 1;
            index >>= 1;
        }
        return count;
    }
}
