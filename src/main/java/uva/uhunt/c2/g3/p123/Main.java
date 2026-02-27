package uva.uhunt.c2.g3.p123;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * 123 - Searching Quickly
 * Time limit: 3.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=59
 */
public class Main {
    public static void main(String... args) {
        final Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 16));
        final PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 16));

        final List<String> ignoredWords = new ArrayList<>();
        while (in.hasNextLine()) {
            final String ignoredWord = in.nextLine();
            if (ignoredWord.equals("::")) break;
            ignoredWords.add(ignoredWord);
        }

        final List<String> titles = new ArrayList<>();
        while (in.hasNextLine()) {
            final String title = in.nextLine();
            titles.add(title);
        }

        final Solution solution = new Solution(ignoredWords, titles);
        final List<String> kwicIndexTitles = solution.getKwicIndexTitles();

        for (final String title : kwicIndexTitles) {
            out.println(title);
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Solution {
    private final List<String> ignoredWords;
    private final List<String> titles;

    public Solution(final List<String> ignoredWords, final List<String> titles) {
        this.ignoredWords = ignoredWords;
        this.titles = titles;

        this.ignoredWords.sort(String::compareToIgnoreCase);
    }

    public List<String> getKwicIndexTitles() {
        final List<KwicIndex> kwicIndices = new ArrayList<>();

        // title and keyword sequence
        int sequence = 0;

        for (String originalTitle : titles) {

            for (int left = 0; left < originalTitle.length(); ) {
                // find next alphabetic character
                while (left < originalTitle.length() && !Character.isAlphabetic(originalTitle.charAt(left))) left++;

                // find next whitespace character
                int right = left;
                while (right < originalTitle.length() && Character.isAlphabetic(originalTitle.charAt(right))) right++;

                // index using kwic
                final String keyword = originalTitle.substring(left, right).toUpperCase();
                final String beforeTitle = originalTitle.substring(0, left).toLowerCase();
                final String afterTitle = originalTitle.substring(right).toLowerCase();
                final String kwicTitle = beforeTitle + keyword + afterTitle;

                // index if not ignored
                if (!isIgnored(keyword)) {
                    final KwicIndex kwicIndex = new KwicIndex(kwicTitle, keyword, sequence++);
                    kwicIndices.add(kwicIndex);
                }

                // next window
                left = right;
            }
        }

        kwicIndices.sort(KwicIndex::compareTo);
        return kwicIndices.stream()
                .map(KwicIndex::getTitle)
                .collect(Collectors.toList());
    }

    private boolean isIgnored(final String word) {
        final int idx = Collections.binarySearch(ignoredWords, word, String::compareToIgnoreCase);
        return 0 <= idx && idx < ignoredWords.size();
    }
}

class KwicIndex implements Comparable<KwicIndex> {
    private final String title;
    private final String keyword;
    private final int sequence;

    public KwicIndex(
            final String title,
            final String keyword,
            final int sequence
    ) {
        this.title = title;
        this.keyword = keyword;
        this.sequence = sequence;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public int compareTo(final KwicIndex o) {
        final int compare1 = keyword.compareToIgnoreCase(o.keyword);
        final int compare2 = Integer.compare(sequence, o.sequence);
        return compare1 != 0 ? compare1 : compare2;
    }
}
