package dev.rizaldi.uhunt.c4.p11101;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * 11101 - Mall Mania
 * Time limit: 3.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=2042
 */
public class Main {
    public static void main(String... args) {
        Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 16));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 16));

        while (true) {
            int totalPerimeters1 = in.nextInt();
            if (totalPerimeters1 == 0) break;
            Point[] perimeters1 = new Point[totalPerimeters1];
            for (int i = 0; i < totalPerimeters1; i++) {
                Point point = new Point(in.nextInt(), in.nextInt());
                perimeters1[i] = point;
            }

            int totalPerimeters2 = in.nextInt();
            Point[] perimeters2 = new Point[totalPerimeters2];
            for (int i = 0; i < totalPerimeters2; i++) {
                Point point = new Point(in.nextInt(), in.nextInt());
                perimeters2[i] = point;
            }

            Solution solution = new Solution(perimeters1, perimeters2);
            int distance = solution.getDistance();
            out.println(distance);
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Point {
    public final int x;
    public final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point[] clockwise() {
        return new Point[]{right(), down(), left(), up()};
    }

    public Point left() {
        return new Point(x - 1, y);
    }

    public Point right() {
        return new Point(x + 1, y);
    }

    public Point up() {
        return new Point(x, y - 1);
    }

    public Point down() {
        return new Point(x, y + 1);
    }

    public int distance(Point other) {
        return Math.abs(x - other.x) + Math.abs(y - other.y);
    }
}

class Solution {
    private static final int FIRST_MALL = 1;
    private static final int SECOND_MALL = 2;
    private final Point[] perimeters1;
    private final Point[] perimeters2;
    private int totalRow;
    private int totalCol;
    private int[][] map;
    private int[][] distances;

    public Solution(Point[] perimeters1, Point[] perimeters2) {
        this.perimeters1 = perimeters1;
        this.perimeters2 = perimeters2;
    }

    public int getDistance() {
        setTotalRowAndCol();
        setMap();
        initDistances();
        traverseDistances();
        return getMinimumDistance();
    }

    private void setTotalRowAndCol() {
        for (Point[] perimeters : Arrays.asList(perimeters1, perimeters2)) {
            for (Point perimeter : perimeters) {
                totalRow = max(totalRow, perimeter.x + 1);
                totalCol = max(totalCol, perimeter.y + 1);
            }
        }
    }

    private void setMap() {
        map = new int[totalRow][totalCol];
        fillPerimeters(map, perimeters1, 1);
        fillPerimeters(map, perimeters2, 2);
    }

    private void initDistances() {
        distances = new int[totalRow][totalCol];
        fill(distances, Integer.MAX_VALUE);
    }

    private void traverseDistances() {
        // start from first mall
        LinkedList<Point> pointq = new LinkedList<>();
        LinkedList<Integer> distanceq = new LinkedList<>();
        for (int row = 0; row < totalRow; row++) {
            for (int col = 0; col < totalCol; col++) {
                if (map[row][col] != FIRST_MALL) continue;

                distances[row][col] = 0;
                pointq.add(new Point(row, col));
                distanceq.addLast(0);
            }
        }

        // traverse whole map
        while (!pointq.isEmpty()) {
            Point point = pointq.removeFirst();
            int distance = distanceq.removeFirst();

            int nextDist = distance + 1;
            for (Point nextPoint : point.clockwise()) {
                if (nextPoint.x < 0 || nextPoint.x >= totalRow) continue;
                if (nextPoint.y < 0 || nextPoint.y >= totalCol) continue;
                if (nextDist >= distances[nextPoint.x][nextPoint.y]) continue;

                distances[nextPoint.x][nextPoint.y] = nextDist;
                pointq.addLast(nextPoint);
                distanceq.addLast(nextDist);
            }
        }
    }

    private int getMinimumDistance() {
        int min = Integer.MAX_VALUE;
        for (int row = 0; row < totalRow; row++) {
            for (int col = 0; col < totalCol; col++) {
                if (map[row][col] != SECOND_MALL) continue;
                min = Math.min(min, distances[row][col]);
            }
        }
        return min;
    }

    private int max(int... values) {
        int max = values[0];
        for (int value : values) max = Math.max(max, value);
        return max;
    }

    private void fillPerimeters(int[][] distances, Point[] perimeters, int flag) {
        for (int i = 1; i < perimeters.length; i++) {
            Point start = perimeters[i - 1], curr = start, end = perimeters[i];

            while (curr.distance(end) != 0) {
                distances[curr.x][curr.y] = flag;

                Point[] candidates = curr.clockwise();
                Arrays.sort(candidates, Comparator.comparingInt(p -> p.distance(end)));
                curr = candidates[0];
            }
        }

        Point last = perimeters[perimeters.length - 1];
        distances[last.x][last.y] = flag;
    }

    private void fill(int[][] array2, int flag) {
        for (int[] array1 : array2) Arrays.fill(array1, flag);
    }
}
