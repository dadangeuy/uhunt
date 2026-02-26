package uva.c1.g5.p245;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * 245 - Uncompress
 * Time limit: 3.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=181
 */
public class Main {
    public static void main(String... args) {
        Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 16));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 16));

        StringBuilder message = new StringBuilder();
        while (in.hasNextLine()) {
            String line = in.nextLine();
            if (line.equals("0")) break;

            message.append(line).append('\n');
        }

        Solution solution = new Solution(message.toString());
        String decompressedMessage = solution.decompress();

        out.print(decompressedMessage);

        in.close();
        out.flush();
        out.close();
    }
}

class Solution {
    private final String message;

    public Solution(String message) {
        this.message = message;
    }

    public String decompress() {
        StringBuilder decompressed = new StringBuilder();
        LinkedList<String> prevWords = new LinkedList<>();

        for (int i = 0; i < message.length(); ) {
            if (digit(message.charAt(i))) {
                int j = i + 1;
                while (j < message.length() && digit(message.charAt(j))) j++;
                int location = Integer.parseInt(message.substring(i, j));
                String word = prevWords.remove(location - 1);
                prevWords.addFirst(word);
                decompressed.append(word);

                i = j;
                continue;
            } else if (alphabet(message.charAt(i))) {
                int j = i + 1;
                while (j < message.length() && alphabet(message.charAt(j))) j++;
                String word = message.substring(i, j);
                prevWords.addFirst(word);
                decompressed.append(word);

                i = j;
            }

            decompressed.append(message.charAt(i));
            i++;
        }

        return decompressed.toString();
    }

    private boolean digit(char letter) {
        return between('0', letter, '9');
    }

    private boolean alphabet(char letter) {
        return between('a', letter, 'z') || between('A', letter, 'Z');
    }

    private boolean between(char left, char mid, char right) {
        return left <= mid && mid <= right;
    }
}
