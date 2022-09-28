package dev.rizaldi.uhunt.helper;

import java.io.IOException;
import java.io.InputStream;
import java.util.function.Function;

public class FastReader {
    private final InputStream stream;
    private final byte[] buffer;
    private int length = 0;
    private int offset = 0;

    public FastReader(InputStream stream, int bufferSize) {
        this.stream = stream;
        this.buffer = new byte[bufferSize];
    }

    public int nextInt() throws IOException {
        skipUntil(b -> Character.isDigit(b) || b == '-');
        boolean negative = buffer[offset] == '-';
        if (negative) offset++;

        int value = 0;
        while (true) {
            if (exhausted()) read();
            if (end()) return value;
            if (!Character.isDigit(buffer[offset])) return negative ? -value : value;
            value *= 10;
            value += buffer[offset] - '0';
            offset++;
        }
    }

    public String next() throws IOException {
        skipUntil(b -> !Character.isWhitespace(b));

        StringBuilder value = new StringBuilder();
        while (true) {
            if (exhausted()) read();
            if (end()) return value.toString();
            if (Character.isWhitespace(buffer[offset])) return value.toString();
            value.append((char) buffer[offset]);
            offset++;
        }
    }

    private void skipUntil(Function<Byte, Boolean> criteria) throws IOException {
        while (true) {
            if (exhausted()) read();
            if (end()) return;
            if (criteria.apply(buffer[offset])) return;
            ++offset;
        }
    }

    private boolean end() {
        return length == -1;
    }

    private boolean exhausted() {
        return offset >= length;
    }

    private void read() throws IOException {
        length = stream.read(buffer);
        offset = 0;
    }

    public void close() throws IOException {
        stream.close();
    }
}
