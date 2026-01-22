package uhunt.c4.g3.p1103;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;

/**
 * 1103 - Ancient Messages
 * Time limit: 3.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=3544
 */
public class Main {
    public static void main(final String... args) throws IOException {
        final BufferedReader in = new BufferedReader(new InputStreamReader(System.in), 1 << 8);
        final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out), 1 << 8);
        final Process process = new Process();

        for (int testCase = 1; ; testCase++) {
            final String heightAndWidth = in.readLine();
            final boolean isEOF = heightAndWidth.equals("0 0");
            if (isEOF) break;

            final int height = Integer.parseInt(heightAndWidth.split(" ")[0]);
            final char[][] image = new char[height][];
            for (int i = 0; i < height; i++) image[i] = in.readLine().toCharArray();

            final Input input = new Input();
            input.image = image;

            final Output output = process.process(input);
            out.write(String.format("Case %d: %s\n", testCase, new String(output.hieroglyphs)));
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Input {
    public char[][] image;
}

class Output {
    public char[] hieroglyphs;
}

class Process {
    public Output process(final Input input) {
        final Output output = new Output();

        final StringBuilder hieroglyphsBuilder = new StringBuilder();

        final char[][] image = preprocessImage(input.image);
        floodFillOuter(image);

        // scan direction: top-bottom, left-right
        for (int row = 0; row < image.length; row++) {
            for (int col = 0; col < image[0].length; col++) {
                final char pixel = image[row][col];
                if (pixel == Pixels.BLACK) {
                    final int innerCount = floodFillSymbol(image, new Cell(row, col));
                    final char hieroglyph = Hieroglyphs.getHieroglyphs(innerCount);
                    hieroglyphsBuilder.append(hieroglyph);
                }
            }
        }

        final char[] hieroglyps = hieroglyphsBuilder.toString().toCharArray();
        Arrays.sort(hieroglyps);
        output.hieroglyphs = hieroglyps;

        return output;
    }

    private char[][] preprocessImage(final char[][] rawImage) {
        return fillBinaryImage(convertHexToBinaryImage(rawImage));
    }

    private char[][] convertHexToBinaryImage(final char[][] hexImage) {
        final char[][] binaryImage = new char[hexImage.length][];
        for (int i = 0; i < hexImage.length; i++) {
            final Hexadecimal hexadecimal = new Hexadecimal(new String(hexImage[i]));
            final char[] binary = hexadecimal.toBinary().toCharArray();
            binaryImage[i] = binary;
        }
        return binaryImage;
    }

    private char[][] fillBinaryImage(final char[][] binaryImage) {
        final char[][] fillImage = new char[binaryImage.length][];
        final int maxWidth = Arrays.stream(binaryImage)
                .mapToInt(row -> row.length)
                .max()
                .orElse(0);

        for (int i = 0; i < binaryImage.length; i++) {
            final char[] binaryRow = binaryImage[i];
            final char[] fillRow = new char[maxWidth];
            fillImage[i] = fillRow;

            Arrays.fill(fillRow, '0');
            for (int j = binaryRow.length - 1, k = fillRow.length - 1; j >= 0; j--, k--) {
                fillRow[k] = binaryRow[j];
            }
        }

        return fillImage;
    }

    private void floodFillOuter(
            final char[][] image
    ) {
        final int topRow = 0;
        final int bottomRow = image.length - 1;

        final int leftCol = 0;
        final int rightCol = image[0].length - 1;

        for (int row = topRow; row <= bottomRow; row++) {
            final char leftPixel = image[row][leftCol];
            final char rightPixel = image[row][rightCol];

            if (leftPixel == Pixels.WHITE) {
                floodFill(image, new Cell(row, leftCol), Pixels.WHITE, Pixels.OUTER);
            }
            if (rightPixel == Pixels.WHITE) {
                floodFill(image, new Cell(row, rightCol), Pixels.WHITE, Pixels.OUTER);
            }
        }

        for (int col = leftCol; col <= rightCol; col++) {
            final char topPixel = image[topRow][col];
            final char bottomPixel = image[bottomRow][col];

            if (topPixel == Pixels.WHITE) {
                floodFill(image, new Cell(topRow, col), Pixels.WHITE, Pixels.OUTER);
            }
            if (bottomPixel == Pixels.WHITE) {
                floodFill(image, new Cell(bottomRow, col), Pixels.WHITE, Pixels.OUTER);
            }
        }
    }

    private int floodFillSymbol(
            final char[][] image,
            final Cell start
    ) {
        final LinkedList<Cell> queue = new LinkedList<>();

        image[start.row][start.col] = Pixels.SYMBOL;
        queue.addLast(start);
        int count = 0;

        while (!queue.isEmpty()) {
            final Cell current = queue.removeFirst();
            for (final Cell next : current.neighbours()) {
                final boolean validRow = 0 <= next.row && next.row < image.length;
                final boolean validRowCol = validRow && 0 <= next.col && next.col < image[next.row].length;
                if (!validRowCol) continue;

                final char pixel = image[next.row][next.col];
                final boolean isSymbol = pixel == Pixels.BLACK;
                final boolean isInner = pixel == Pixels.WHITE;
                if (isSymbol) {
                    image[next.row][next.col] = Pixels.SYMBOL;
                    queue.addLast(next);
                } else if (isInner) {
                    count++;
                    floodFill(image, next, Pixels.WHITE, Pixels.INNER);
                }
            }
        }

        return count;
    }

    private void floodFill(
            final char[][] image,
            final Cell start,
            final char target,
            final char fill
    ) {
        final LinkedList<Cell> queue = new LinkedList<>();

        image[start.row][start.col] = fill;
        queue.addLast(start);

        while (!queue.isEmpty()) {
            final Cell current = queue.removeFirst();
            for (final Cell next : current.neighbours()) {
                final boolean validRow = 0 <= next.row && next.row < image.length;
                final boolean validRowCol = validRow && 0 <= next.col && next.col < image[next.row].length;
                if (!validRowCol) continue;

                final boolean isTarget = image[next.row][next.col] == target;
                if (!isTarget) continue;

                image[next.row][next.col] = fill;
                queue.addLast(next);
            }
        }
    }
}

class Hexadecimal {
    public final String value;

    public Hexadecimal(final String value) {
        this.value = value;
    }

    public String toBinary() {
        final BigInteger bigInteger = new BigInteger(value, 16);
        return bigInteger.toString(2);
    }
}

class Hieroglyphs {
    // 1 inside white
    public static final char ANKH = 'A';
    // 3 inside white
    public static final char WEDJAT = 'J';
    // 5 inside white
    public static final char DJED = 'D';
    // 4 inside white
    public static final char SCARAB = 'S';
    // 0 inside white
    public static final char WAS = 'W';
    // 2 inside white
    public static final char AKHET = 'K';
    private static final char[] SEQUENCE = new char[]{WAS, ANKH, AKHET, WEDJAT, SCARAB, DJED};

    public static char getHieroglyphs(final int innerCount) {
        return SEQUENCE[innerCount];
    }
}

class Pixels {
    public static final char WHITE = '0';
    public static final char BLACK = '1';

    public static final char OUTER = 'A';
    public static final char INNER = 'B';
    public static final char SYMBOL = 'C';
}

class Cell implements Comparable<Cell> {
    public static final Comparator<Cell> COMPARATOR = Comparator
            .comparingInt((Cell p) -> p.row)
            .thenComparingInt((Cell p) -> p.col);
    public final int row;
    public final int col;

    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public Cell[] neighbours() {
        return new Cell[]{
                new Cell(row - 1, col),
                new Cell(row + 1, col),
                new Cell(row, col - 1),
                new Cell(row, col + 1),
        };
    }

    @Override
    public int compareTo(Cell other) {
        return COMPARATOR.compare(this, other);
    }
}
