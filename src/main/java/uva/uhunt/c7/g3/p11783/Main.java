package uva.uhunt.c7.g3.p11783;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 11783 - Nails
 * Time limit: 1.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=2883
 */
public class Main {
    public static void main(final String... args) throws IOException {
        final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));
        final Process process = new Process();

        while (true) {
            final int totalSticks = Integer.parseInt(readLine(in));
            final boolean isEOF = totalSticks == 0;
            if (isEOF) break;

            final int[][] sticks = new int[totalSticks][];
            for (int i = 0; i < totalSticks; i++) {
                final int[] stick = Arrays.stream(readSplitLine(in))
                    .mapToInt(Integer::parseInt)
                    .toArray();
                sticks[i] = stick;
            }

            final Input input = new Input();
            input.totalSticks = totalSticks;
            input.sticks = sticks;

            final Output output = process.process(input);
            out.write(Integer.toString(output.totalNails));
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
    public int totalSticks;
    public int[][] sticks;
}

class Output {
    public int totalNails;
}

class Process {
    public Output process(final Input input) {
        final Output output = new Output();

        final List<Segment> segments = Arrays.stream(input.sticks)
            .map(Segment::new)
            .collect(Collectors.toList());

        final Set<Segment> unnailedSegments = new HashSet<>(segments);
        final List<Point> nailedPoints = new ArrayList<>();

        for (int i = 0; i < segments.size() - 1; i++) {
            final Segment first = segments.get(i);
            for (int j = i + 1; j < segments.size(); j++) {
                final Segment second = segments.get(j);

                final Optional<Point> intersection = first.findIntersection(second);
                if (intersection.isPresent()) {
                    nailedPoints.add(intersection.get());
                    unnailedSegments.remove(first);
                    unnailedSegments.remove(second);
                }
            }
        }

        for (final Segment segment : unnailedSegments) {
            nailedPoints.add(segment.head);
            nailedPoints.add(segment.tail);
        }

        output.totalNails = nailedPoints.size();
        return output;
    }
}

class Segment {
    public Point head;
    public Point tail;
    public Line line;

    public Segment(final int[] values) {
        this.head = new Point(values[0], values[1]);
        this.tail = new Point(values[2], values[3]);
        this.line = new Line(this.head, this.tail);
    }

    // https://cp-algorithms.com/geometry/segments-intersection.html
    public Optional<Point> findIntersection(final Segment other) {
        final Optional<Point> intersection = line.findIntersection(other.line);
        final boolean isValid = intersection.isPresent() &&
                                contains(intersection.get()) &&
                                other.contains(intersection.get());
        return isValid ? intersection : Optional.empty();
    }

    public boolean contains(final Point intersection) {
        final boolean isValidX = between(head.x, intersection.x, tail.x);
        final boolean isValidY = between(head.y, intersection.y, tail.y);
        return isValidX && isValidY;
    }

    private boolean between(final double from, final double value, final double until) {
        return Math.min(from, until) <= value && value <= Math.max(from, until);
    }
}

// ax + by + c = 0
class Line {
    public final double a;
    public final double b;
    public final double c;

    // https://cp-algorithms.com/geometry/segment-to-line.html
    public Line(final Point head, final Point tail) {
        this.a = head.y - tail.y;
        this.b = tail.x - head.x;
        this.c = -(this.a * head.x) - (this.b * head.y);
    }

    // https://cp-algorithms.com/geometry/lines-intersection.html
    public Optional<Point> findIntersection(final Line other) {
        final double denominator = determinant(a, b, other.a, other.b);
        final boolean iSParallel = denominator == 0;
        if (iSParallel) return Optional.empty();

        final double xNumerator = determinant(c, b, other.c, other.b);
        final double x = -xNumerator / denominator;

        final double yNumerator = determinant(a, c, other.a, other.c);
        final double y = -yNumerator / denominator;

        return Optional.of(new Point(x, y));
    }

    public double determinant(final double a, final double b, final double c, final double d) {
        return a * d - b * c;
    }
}

class Point {
    public final double x;
    public final double y;

    public Point(final double x, final double y) {
        this.x = x;
        this.y = y;
    }
}
