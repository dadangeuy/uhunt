package uhunt.c2.p12709;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;

public class Main {
    private static BufferedReader in;
    private static String[] lines;

    public static void main(String... args) throws IOException {
        in = new BufferedReader(new InputStreamReader(System.in));

        int[][] ants = new int[100][3];

        while (true) {
            int totalAnt = Integer.parseInt(in.readLine());
            if (totalAnt == 0) break;

            for (int i = 0; i < totalAnt; i++) {
                lines = in.readLine().split(" ", 3);

                int length = Integer.parseInt(lines[0]);
                int width = Integer.parseInt(lines[1]);
                int height = Integer.parseInt(lines[2]);

                ants[i][0] = length;
                ants[i][1] = width;
                ants[i][2] = height;
            }

            Solution solution = new Solution(totalAnt, ants);
            int volume = solution.getVolumeOfAntWithHighestDownwardAcceleration();

            System.out.println(volume);
        }
    }
}

// sort ants by height (DESC) and volume (DESC), return first ant
class Solution {
    private static final Comparator<int[]> compareByHeightAndVolume = Comparator
        .<int[]>comparingInt(a -> -a[2])
        .thenComparingInt(a -> -volume(a));
    private final int totalAnt;
    private final int[][] ants;

    public Solution(int totalAnt, int[][] ants) {
        this.totalAnt = totalAnt;
        this.ants = ants;
    }

    private static int volume(int[] ant) {
        return ant[0] * ant[1] * ant[2];
    }

    public int getVolumeOfAntWithHighestDownwardAcceleration() {
        Arrays.sort(ants, 0, totalAnt, compareByHeightAndVolume);
        return volume(ants[0]);
    }
}
