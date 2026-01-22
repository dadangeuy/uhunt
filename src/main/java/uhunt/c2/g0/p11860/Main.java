package uhunt.c2.g0.p11860;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 11860 - Document Analyzer
 * Time limit: 5.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&page=show_problem&problem=2960
 */
public class Main {
    public static void main(String... args) {
        Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 16));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 16));

        int totalDocument = in.nextInt();
        in.nextLine();

        for (int i = 0; i < totalDocument; i++) {
            StringBuilder db = new StringBuilder();
            while (true) {
                String line = in.nextLine();
                if (line.equals("END")) break;
                db.append(line).append('\n');
            }

            String document = db.toString();
            Solution solution = new Solution(document);
            int[] range = solution.getRange();

            out.format("Document %d: %d %d\n", i + 1, range[0] + 1, range[1] + 1);
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Solution {
    private static final Pattern WORD_PATTERN = Pattern.compile("[a-z]+");
    private final String document;

    public Solution(String document) {
        this.document = document;
    }

    public int[] getRange() {
        String[] words = getWords();
        int total = (int) Arrays.stream(words).distinct().count();

        Map<String, Integer> counts = new HashMap<>();
        int left = 0, right = 0;
        int[] minimumRange = new int[]{0, words.length - 1};

        while (right < words.length) {
            while (counts.size() < total && right < words.length) {
                String word = words[right++];
                counts.compute(word, (k, v) -> v == null ? 1 : v + 1);
            }
            while (counts.size() == total && left <= right) {
                int[] range = new int[]{left, right - 1};
                minimumRange = min(minimumRange, range);

                String word = words[left++];
                counts.computeIfPresent(word, (k, v) -> v == 1 ? null : v - 1);
            }
        }

        return minimumRange;
    }

    private String[] getWords() {
        LinkedList<String> words = new LinkedList<>();
        Matcher matcher = WORD_PATTERN.matcher(document);
        while (matcher.find()) words.addLast(matcher.group());
        return words.toArray(new String[0]);
    }

    private int[] min(int[] range1, int[] range2) {
        int d1 = diff(range1), d2 = diff(range2);
        if (d1 < d2) return range1;
        if (d1 > d2) return range2;
        if (range1[0] <= range2[0]) return range1;
        return range2;
    }

    private int diff(int[] range) {
        return range[1] - range[0];
    }
}
