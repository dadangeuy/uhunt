package uva.common.template.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

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
