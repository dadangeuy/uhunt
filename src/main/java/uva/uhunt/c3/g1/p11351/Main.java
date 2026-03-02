package uva.uhunt.c3.g1.p11351;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

/**
 * 11351 - Last Man Standing
 * Time limit: 1.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&page=show_problem&problem=2326
 */
public class Main {
    public static void main(String... args) throws IOException {
        final BufferedIO io = new BufferedIO(System.in, System.out);
        final Process process = new Process();

        final int totalTestCases = Integer.parseInt(io.readLine());
        for (int i = 0; i < totalTestCases; i++) {
            final Input input = new Input();
            input.caseId = i + 1;
            final String[] lines = io.readLines(" ");
            input.totalPeoples = Integer.parseInt(lines[0]);
            input.interval = Integer.parseInt(lines[1]);

            final Output output = process.process(input);
            io.write("Case %d: %d\n", output.caseId, output.survivor);
        }

        io.close();
    }
}

final class BufferedIO {
    private final BufferedReader in;
    private final BufferedWriter out;

    public BufferedIO(final InputStream in, final OutputStream out) {
        this.in = new BufferedReader(new InputStreamReader(in));
        this.out = new BufferedWriter(new OutputStreamWriter(out));
    }

    public String[] readLines(final String separator) throws IOException {
        final String line = readLine();
        return line.split(separator);
    }

    public String readLine() throws IOException {
        String line = in.readLine();
        while (line != null && line.isEmpty()) line = in.readLine();
        return line;
    }

    public BufferedIO write(final String format, Object... args) throws IOException {
        final String string = String.format(format, args);
        return write(string);
    }

    public BufferedIO write(final String string) throws IOException {
        out.write(string);
        return this;
    }

    public void close() throws IOException {
        in.close();
        out.flush();
        out.close();
    }
}

class Input {
    public int caseId;
    public int totalPeoples;
    public int interval;
}

class Output {
    public int caseId;
    public int survivor;
}

class Process {
    public Output process(final Input input) {
        final Output output = new Output();
        output.caseId = input.caseId;
        output.survivor = josephus(input.totalPeoples, input.interval) + 1;
        return output;
    }

    private int josephus(final int totalPeoples, final int interval) {
        if (totalPeoples == 0) return 1;
        return (josephus(totalPeoples - 1, interval) + interval) % totalPeoples;
    }
}
