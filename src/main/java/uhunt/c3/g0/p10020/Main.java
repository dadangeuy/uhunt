package uhunt.c3.g0.p10020;

import java.util.*;

public class Main {
    public static void main(String... args) {
        Scanner in = new Scanner(System.in);
        int totalTest = in.nextInt();
        for (int test = 0; test < totalTest; test++) {
            if (test > 0) System.out.println();

            int target = in.nextInt();
            List<int[]> segments = new LinkedList<>();
            while (true) {
                int left = in.nextInt();
                int right = in.nextInt();
                if (left == 0 && right == 0) break;
                int[] segment = new int[]{left, right};
                segments.add(segment);
            }

            Solution solution = new Solution(target, segments);
            Collection<int[]> minimalSegments = solution.getMinimalCoveredSegments();

            System.out.println(minimalSegments.size());
            minimalSegments.forEach(s -> System.out.format("%d %d\n", s[0], s[1]));
        }
    }
}

class Solution {
    private static final Comparator<int[]> SORT_SEGMENT_ASC = Comparator
            .<int[]>comparingInt(arr -> arr[0])
            .thenComparingInt(arr -> arr[1]);
    private final int target;
    private final int[][] segments;

    public Solution(int target, List<int[]> segments) {
        this.target = target;
        this.segments = segments.toArray(new int[0][]);

        Arrays.sort(this.segments, SORT_SEGMENT_ASC);
    }

    public Collection<int[]> getMinimalCoveredSegments() {
        LinkedList<int[]> segmentq = new LinkedList<>();
        LinkedList<int[]> coverageq = new LinkedList<>();
        int progress = 0;

        for (int[] segment : segments) {
            boolean covered = segment[0] <= progress && progress < segment[1];
            if (!covered) continue;

            while (!coverageq.isEmpty()) {
                int[] prevCoverage = coverageq.peekLast();
                boolean hasBetterCoverage = segment[0] <= prevCoverage[0] && prevCoverage[1] < segment[1];
                if (!hasBetterCoverage) break;

                coverageq.removeLast();
                segmentq.removeLast();
                progress = prevCoverage[0];
            }

            boolean done = progress >= target;
            if (done) break;

            coverageq.addLast(new int[]{progress, segment[1]});
            segmentq.addLast(segment);
            progress = segment[1];
        }

        if (progress < target) return Collections.emptyList();
        return segmentq;
    }
}
