package uva.c2.g4.p414;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * 414 - Machined Surfaces
 * Time limit: 3.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=355
 */
public class Main {
    public static void main(String... args) {
        final Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 16));
        final PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 16));

        while (in.hasNextLine()) {
            int totalRow = Integer.parseInt(in.nextLine());
            if (totalRow == 0) break;

            char[][] image = new char[totalRow][];
            for (int i = 0; i < totalRow; i++) image[i] = in.nextLine().toCharArray();

            int countVoid = countVoid(image);

            out.println(countVoid);
        }

        in.close();
        out.flush();
        out.close();
    }

    private static int countVoid(char[][] image) {
        int maxCountMarked = maxCountMarked(image);
        int countVoid = 0;
        for (char[] imageRow : image) countVoid += maxCountMarked - countMarked(imageRow);
        return countVoid;
    }

    private static int maxCountMarked(char[][] image) {
        int maxCount = 0;
        for (char[] imageRow : image) maxCount = Math.max(maxCount, countMarked(imageRow));
        return maxCount;
    }

    private static int countMarked(char[] imageRow) {
        int count = 0;
        for (char imageCell : imageRow) count += imageCell == 'X'? 1 : 0;
        return count;
    }
}
