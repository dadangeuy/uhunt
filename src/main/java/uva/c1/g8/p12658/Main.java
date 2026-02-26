package uva.c1.g8.p12658;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {
    public static void main(String... args) throws Exception {
        Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 16));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 16));

        in.nextInt();
        char[][] image = new char[5][];
        for (int i = 0; i < 5; i++) image[i] = in.next().toCharArray();

        for (int i = 0; i < image[0].length; i += 4) {
            if (isOne(image, i, i + 2)) out.print(1);
            else if (isTwo(image, i, i + 2)) out.print(2);
            else if (isThree(image, i, i + 2)) out.print(3);
            else throw new Exception("unknown digit");
        }
        out.println();

        in.close();
        out.flush();
        out.close();
    }

    private static boolean isOne(char[][] image, int left, int right) {
        return ".*.".equals(fromImage(image, 0, left, right));
    }

    private static boolean isTwo(char[][] image, int left, int right) {
        return "*..".equals(fromImage(image, 3, left, right));
    }

    private static boolean isThree(char[][] image, int left, int right) {
        return "..*".equals(fromImage(image, 3, left, right));
    }

    private static String fromImage(char[][] image, int row, int left, int right) {
        return new String(image[row], left, right - left + 1);
    }
}
