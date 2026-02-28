package uva.common.template.io;

import java.io.IOException;
import java.io.InputStream;
import java.util.function.Function;

@SuppressWarnings("unused")
public final class FastReader {
    private final InputStream stream;
    private final byte[] buffer;
    private int length = 0;
    private int offset = 0;
    private final byte[] readBuffer;

    public FastReader(InputStream stream, int bufferSize) {
        this.stream = stream;
        this.buffer = new byte[bufferSize];
        this.readBuffer = new byte[bufferSize];
    }

    public boolean hasNextInt() throws IOException {
        skipUntil(b -> !Character.isWhitespace(b));
        return !end() && (buffer[offset] == '-' || Character.isDigit(buffer[offset]));
    }

    public int nextInt() throws IOException {
        if (!hasNextInt()) return 0;

        boolean negative = buffer[offset] == '-';
        if (negative) offset++;

        int length = readWhile(Character::isDigit, readBuffer);
        int value = 0;
        for (int i = 0; i < length; i++) value = value * 10 + readBuffer[i] - '0';
        return negative ? -value : value;
    }

    public boolean hasNext() throws IOException {
        skipUntil(b -> !Character.isWhitespace(b));
        return !end();
    }

    public String next() throws IOException {
        if (!hasNext()) return "";

        int length = readWhile(b -> !Character.isWhitespace(b), readBuffer);
        return new String(readBuffer, 0, length);
    }

    private void skipUntil(Function<Byte, Boolean> criteria) throws IOException {
        while (true) {
            if (exhausted()) read();
            if (end()) return;
            if (criteria.apply(buffer[offset])) return;
            ++offset;
        }
    }

    private int readWhile(Function<Byte, Boolean> criteria, byte[] output) throws IOException {
        for (int i = 0; ; i++) {
            if (exhausted()) read();
            if (end()) return i;
            if (!criteria.apply(buffer[offset])) return i;
            output[i] = buffer[offset];
            offset++;
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