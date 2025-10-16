package uhunt.c2.p13181;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * 13181 - Sleeping in hostels
 * Time limit: 1.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=5092
 */
public class Main {
    public static void main(String... args) {
        Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 16));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 16));

        while (in.hasNextLine()) {
            char[] beds = in.nextLine().toCharArray();

            Solution solution = new Solution(beds);
            int distance = solution.getMaxDistanceToNearestOccupiedBed();

            out.println(distance);
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Solution {
    private final char[] beds;

    public Solution(char[] beds) {
        this.beds = beds;
    }

    public int getMaxDistanceToNearestOccupiedBed() {
        int[] occupiedIds = findIds(beds, 'X');

        // no occupied bed
        if (occupiedIds.length == 0) return beds.length - 1;

        int maxDistance = 0;

        // take between occupied bed
        for (int i = 0; i < occupiedIds.length - 1; i++) {
            int leftBed = occupiedIds[i], rightBed = occupiedIds[i + 1];
            int distance = (rightBed - leftBed - 2) / 2;
            maxDistance = Math.max(maxDistance, distance);
        }

        // take first bed
        int firstOccupiedBed = occupiedIds[0], firstBed = 0;
        maxDistance = Math.max(maxDistance, firstOccupiedBed - firstBed - 1);

        // take last bed
        int lastOccupiedBed = occupiedIds[occupiedIds.length - 1], lastBed = beds.length - 1;
        maxDistance = Math.max(maxDistance, lastBed - lastOccupiedBed - 1);

        return maxDistance;
    }

    private int[] findIds(char[] array, char target) {
        int count = count(array, target);
        int[] ids = new int[count];
        for (int i = 0, j = 0; i < array.length; i++) if (array[i] == target) ids[j++] = i;
        return ids;
    }

    private int count(char[] array, char target) {
        int count = 0;
        for (char value : array) if (value == target) count++;
        return count;
    }
}
