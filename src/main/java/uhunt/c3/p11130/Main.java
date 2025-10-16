package uhunt.c3.p11130;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 11130 - Billiard bounces
 * Time limit: 3.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&page=show_problem&problem=2071#google_vignette
 */
public class Main {
    private static final String EOF_LINE = "0 0 0 0 0";
    private static final String SEPARATOR = " ";
    private static final String CASE_FORMAT = "%d %d\n";

    public static void main(final String... args) throws IOException {
        final BufferedReader in = new BufferedReader(new InputStreamReader(System.in), 1 << 16);
        final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out), 1 << 16);
        final Process process = new Process();

        for (String line = in.readLine(); !EOF_LINE.equals(line); line = in.readLine()) {
            final String[] lines = line.split(SEPARATOR);
            final Input input = new Input(
                    Integer.parseInt(lines[0]),
                    Integer.parseInt(lines[1]),
                    Integer.parseInt(lines[2]),
                    Integer.parseInt(lines[3]),
                    Integer.parseInt(lines[4])
            );

            final Output output = process.process(input);
            out.write(String.format(CASE_FORMAT, output.countHitVerticalWall, output.countHitHorizontalWall));
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Input {
    public final int horizontalLength;
    public final int verticalLength;
    public final int initialVelocity;
    public final int initialAngle;
    public final int duration;

    public Input(
            final int horizontalLength,
            final int verticalLength,
            final int initialVelocity,
            final int initialAngle,
            final int duration
    ) {
        this.horizontalLength = horizontalLength;
        this.verticalLength = verticalLength;
        this.initialVelocity = initialVelocity;
        this.initialAngle = initialAngle;
        this.duration = duration;
    }
}

class Output {
    public final int countHitHorizontalWall;
    public final int countHitVerticalWall;

    public Output(
            final int countHitHorizontalWall,
            final int countHitVerticalWall
    ) {
        this.countHitHorizontalWall = countHitHorizontalWall;
        this.countHitVerticalWall = countHitVerticalWall;
    }
}

class Process {
    /**
     * Mathematical simulation of Billiard.
     * Move the ball forward in the direction of the angle until it reaches the maximum distance.
     * When reaching an edge, the angle of the ball should be bounced before continuing with the movement.
     */
    public Output process(final Input input) {
        final Count count = new Count();

        final Rectangle tableBoundary = createTableBoundary(input);
        Movement ballMovement = createInitialBallMovement(input);

        while (ballMovement.couldMove()) {
            final List<RightTriangle> ballTrajectories = createBallTrajectories(tableBoundary, ballMovement);
            final List<Movement> nextBallMovements = createNextBallMovements(tableBoundary, ballMovement, ballTrajectories);
            final Movement shortestNextBallMovement = findShortestBallMovement(nextBallMovements);

            final Movement bounceShortestNextBallMovement = shortestNextBallMovement.bounce(tableBoundary);
            if (shortestNextBallMovement.onHorizontalEdge(tableBoundary)) count.horizontal++;
            if (shortestNextBallMovement.onVerticalEdge(tableBoundary)) count.vertical++;

            ballMovement = bounceShortestNextBallMovement;
        }

        return new Output(count.horizontal, count.vertical);
    }

    /**
     * Create boundary of the billiard's table based on the input.
     */
    private Rectangle createTableBoundary(final Input input) {
        return new Rectangle(
                input.horizontalLength,
                input.verticalLength
        );
    }

    /**
     * Create initial movement of the billiard's ball based on the input.
     * The maximum distance is calculated using the kinematic equation with assumed constant deceleration.
     * The starting position is located at the center of the billiard's table.
     */
    private Movement createInitialBallMovement(final Input input) {
        final double initialVelocity = input.initialVelocity;
        final double totalTime = input.duration;
        final double constantDeceleration = -(initialVelocity / totalTime);
        final double totalDistance = findDistance(initialVelocity, constantDeceleration, totalTime);

        return new Movement(
                input.horizontalLength / 2.0,
                input.verticalLength / 2.0,
                input.initialAngle,
                totalDistance,
                0.0
        );
    }

    /**
     * Find distance based on kinematic equation (s = (u * t) + ((a * t * t) / 2)).
     */
    private double findDistance(
            final double initialVelocity,
            final double acceleration,
            final double time
    ) {
        return (initialVelocity * time) + ((acceleration * time * time) / 2);
    }

    /**
     * Create possible trajectories based on 5 strategies: reaching the edges (up/down/left/right) and maximum distance.
     * The boundary is ignored, meaning the trajectories could lead to the outside of the table.
     */
    private List<RightTriangle> createBallTrajectories(
            final Rectangle boundary,
            final Movement movement
    ) {
        // opposite = distance to up/down edge
        final RightTriangle upEdge = RightTriangle.fromOpposite(-(boundary.up - movement.vertical), movement.angle);
        final RightTriangle downEdge = RightTriangle.fromOpposite(-(boundary.down - movement.vertical), movement.angle);

        // adjacent = distance to left/right edge
        final RightTriangle leftEdge = RightTriangle.fromAdjacent(boundary.left - movement.horizontal, movement.angle);
        final RightTriangle rightEdge = RightTriangle.fromAdjacent(boundary.right - movement.horizontal, movement.angle);

        // hypotenuse = remaining distance
        final RightTriangle maximumDistance = RightTriangle.fromHypotenuse(movement.maximumDistance - movement.cumulativeDistance, movement.angle);

        // since the hypotenuse equals to the travel distance, it must be positive to move the ball forward
        return Stream.of(upEdge, downEdge, leftEdge, rightEdge, maximumDistance)
                .filter(t -> t.hypotenuse > 0)
                .collect(Collectors.toList());
    }

    /**
     * Create next movements based on possible trajectories.
     * The movements should be within the boundary of the table and not exceeding the maximum distance.
     */
    public List<Movement> createNextBallMovements(
            final Rectangle boundary,
            final Movement movement,
            final List<RightTriangle> trajectories
    ) {
        return trajectories.stream()
                .map(movement::move)
                .filter(m -> m.isValid(boundary))
                .collect(Collectors.toList());
    }

    /**
     * Find the shortest movement (minimizing cumulative distance).
     */
    private Movement findShortestBallMovement(final List<Movement> movements) {
        return movements.stream()
                .min(Comparator.comparingDouble(m -> m.cumulativeDistance))
                .orElseThrow(NullPointerException::new);
    }
}

/**
 * Right-angled triangle that is calculated using trigonometric ratios.
 * Formulas:
 * 1. Sin(angle) = opposite / hypotenuse
 * 2. Cos(angle) = adjacent / hypotenuse
 * 3. Tan(angle) = opposite / adjacent
 */
class RightTriangle {
    public final double hypotenuse;
    public final double opposite;
    public final double adjacent;
    public final double angle;

    public static RightTriangle fromOpposite(
            final double opposite,
            final double angle
    ) {
        double hypotenuse = opposite / Math.sin(Math.toRadians(angle));
        double adjacent = opposite / Math.tan(Math.toRadians(angle));

        return new RightTriangle(hypotenuse, opposite, adjacent, angle);
    }

    public static RightTriangle fromAdjacent(
            final double adjacent,
            final double angle
    ) {
        double hypotenuse = adjacent / Math.cos(Math.toRadians(angle));
        double opposite = Math.tan(Math.toRadians(angle)) * adjacent;

        return new RightTriangle(hypotenuse, opposite, adjacent, angle);
    }

    public static RightTriangle fromHypotenuse(
            final double hypotenuse,
            final double angle
    ) {
        double opposite = Math.sin(Math.toRadians(angle)) * hypotenuse;
        double adjacent = Math.cos(Math.toRadians(angle)) * hypotenuse;

        return new RightTriangle(hypotenuse, opposite, adjacent, angle);
    }

    public RightTriangle(
            final double hypotenuse,
            final double opposite,
            final double adjacent,
            final double angle
    ) {
        this.hypotenuse = hypotenuse;
        this.opposite = opposite;
        this.adjacent = adjacent;
        this.angle = angle;
    }
}

class Rectangle {
    public final double horizontalLength;
    public final double verticalLength;

    public final double left;
    public final double right;
    public final double up;
    public final double down;

    public Rectangle(
            final double horizontalLength,
            final double verticalLength
    ) {
        this.horizontalLength = horizontalLength;
        this.verticalLength = verticalLength;

        this.left = 0;
        this.right = horizontalLength;
        this.up = 0;
        this.down = verticalLength;
    }

    public boolean inside(
            final double horizontal,
            final double vertical
    ) {
        return Util.between(left, horizontal, right) && Util.between(up, vertical, down);
    }
}

class Movement {
    public final double horizontal;
    public final double vertical;
    public final double angle;
    public final double maximumDistance;
    public final double cumulativeDistance;

    public Movement(
            final double horizontal,
            final double vertical,
            final double angle,
            final double maximumDistance,
            final double cumulativeDistance
    ) {
        this.horizontal = horizontal;
        this.vertical = vertical;
        this.angle = angle;
        this.maximumDistance = maximumDistance;
        this.cumulativeDistance = cumulativeDistance;
    }

    /**
     * Movement is valid if the position is inside the boundary and did not exceed the maximum distance.
     */
    public boolean isValid(final Rectangle boundary) {
        final boolean validPosition = boundary.inside(horizontal, vertical);
        final boolean validDistance = Util.between(0, cumulativeDistance, maximumDistance);

        return validPosition && validDistance;
    }

    /**
     * It is movable if it hasn't reached the maximum distance.
     */
    public boolean couldMove() {
        return Util.lesser(cumulativeDistance, maximumDistance);
    }

    /**
     * Move the position based on the trajectory.
     */
    public Movement move(final RightTriangle trajectory) {
        return new Movement(
                horizontal + trajectory.adjacent,
                vertical - trajectory.opposite,
                angle,
                maximumDistance,
                cumulativeDistance + trajectory.hypotenuse
        );
    }

    /**
     * Bounce the angle based on the position inside the boundary.
     * If the position is on the horizontal edge of the boundary, reflect the angle vertically.
     * If the position is on the vertical edge of the boundary, reflect the angle horizontally.
     */
    public Movement bounce(final Rectangle boundary) {
        Movement bounceMovement = this;

        if (couldMove()) {
            if (onHorizontalEdge(boundary)) {
                bounceMovement = bounceMovement.verticalReflection();
            }
            if (onVerticalEdge(boundary)) {
                bounceMovement = bounceMovement.horizontalReflection();
            }
        }

        return bounceMovement;
    }

    public boolean onHorizontalEdge(final Rectangle boundary) {
        final boolean hitUp = Util.equals(vertical, boundary.up);
        final boolean hitDown = Util.equals(vertical, boundary.down);

        return hitUp || hitDown;
    }

    public boolean onVerticalEdge(final Rectangle boundary) {
        final boolean hitLeft = Util.equals(horizontal, boundary.left);
        final boolean hitRight = Util.equals(horizontal, boundary.right);

        return hitLeft || hitRight;
    }

    /**
     * Reflect the angle across the horizontal axis
     */
    public Movement verticalReflection() {
        return new Movement(
                horizontal,
                vertical,
                (360.0 - angle) % 360.0,
                maximumDistance,
                cumulativeDistance
        );
    }

    /**
     * Reflect the angle across the vertical axis
     */
    public Movement horizontalReflection() {
        return new Movement(
                horizontal,
                vertical,
                (540.0 - angle) % 360.0,
                maximumDistance,
                cumulativeDistance
        );
    }
}

class Count {
    public int horizontal;
    public int vertical;

    public Count() {
        this.horizontal = 0;
        this.vertical = 0;
    }
}

class Util {
    public static final double PRECISION = 0.00000001;

    public static boolean between(final double lower, final double value, final double upper) {
        return (lower - PRECISION) <= value && value <= (upper + PRECISION);
    }

    public static boolean equals(final double a, final double b) {
        return Math.abs(a - b) <= PRECISION;
    }

    public static boolean lesser(final double a, final double b) {
        return a < b - PRECISION;
    }
}
