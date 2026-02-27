package uva.uhunt.c7.g7.p587;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Scanner;

/**
 * 587 - There's treasure everywhere!
 * Time limit: 3.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=528
 */
public class Main {
    public static void main(final String... args) {
        final Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 8));
        final PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 8));
        final Process process = new Process();

        for (int mapId = 1; ; mapId++) {
            final Input input = new Input();
            input.mapId = mapId;
            input.description = in.nextLine();

            final boolean isEOF = "END".equals(input.description);
            if (isEOF) break;

            final Output output = process.process(input);
            out.format(
                "Map #%d\nThe treasure is located at (%.3f,%.3f).\nThe distance to the treasure is %.3f.\n\n",
                output.mapId, round(output.location[0]), round(output.location[1]), round(output.distance)
            );
        }

        in.close();
        out.flush();
        out.close();
    }

    private static BigDecimal round(final double value) {
        return BigDecimal.valueOf(value).setScale(3, RoundingMode.HALF_UP);
    }
}

class Input {
    public int mapId;
    public String description;
}

class Output {
    public int mapId;
    public double[] location;
    public double distance;
}

class Process {
    public Output process(final Input input) {
        final Output output = new Output();

        final double[] startedAt = new double[]{0d, 0d};
        final double[] finishedAt = new double[]{0d, 0d};

        for (final String hint : input.description.split(",")) {
            final int totalSteps = Integer.parseInt(getDigitText(hint));
            final String direction = getAlphabeticText(hint);

            final int degree = getDegree(direction);
            final double horizontalDistance = getHorizontalDistance(totalSteps, degree);
            final double verticalDistance = getVerticalDistance(totalSteps, degree);

            finishedAt[0] += horizontalDistance;
            finishedAt[1] += verticalDistance;
        }

        final double distance = getDistance(startedAt, finishedAt);

        output.mapId = input.mapId;
        output.location = finishedAt;
        output.distance = distance;

        return output;
    }

    private String getDigitText(final String text) {
        final StringBuilder builder = new StringBuilder();
        for (final char letter : text.toCharArray()) {
            if (Character.isDigit(letter)) {
                builder.append(letter);
            }
        }
        return builder.toString();
    }

    private String getAlphabeticText(final String text) {
        final StringBuilder builder = new StringBuilder();
        for (final char letter : text.toCharArray()) {
            if (Character.isAlphabetic(letter)) {
                builder.append(letter);
            }
        }
        return builder.toString();
    }

    private int getDegree(final String direction) {
        switch (direction) {
            case "E":
                return 0;
            case "NE":
                return 45;
            case "N":
                return 90;
            case "NW":
                return 135;
            case "W":
                return 180;
            case "SW":
                return 225;
            case "S":
                return 270;
            case "SE":
                return 315;
        }
        throw new IllegalArgumentException(direction);
    }

    private double getHorizontalDistance(final int hypotenuse, final int degree) {
        return hypotenuse * Math.cos(Math.toRadians(degree));
    }

    private double getVerticalDistance(final int hypotenuse, final int degree) {
        return hypotenuse * Math.sin(Math.toRadians(degree));
    }

    private double getDistance(final double[] location1, final double[] location2) {
        final double h = location1[0] - location2[0];
        final double v = location1[1] - location2[1];
        return Math.sqrt(h * h + v * v);
    }
}
