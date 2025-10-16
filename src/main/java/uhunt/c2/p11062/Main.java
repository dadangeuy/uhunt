package uhunt.c2.p11062;

import java.io.IOException;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

/**
 * 11062 - Andy's Second Dictionary
 * Time limit: 3.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=2003
 */
public class Main {
    public static void main(final String... args) throws IOException {
        final Process process = new Process();

        final Input input = new Input();
        input.article = readUntilEOF();

        final Output output = process.process(input);
        for (final String word : output.dictionary) System.out.println(word);
    }

    private static String readUntilEOF() throws IOException {
        final byte[] bytes = new byte[1 << 16];
        final int length = System.in.read(bytes);

        return new String(Arrays.copyOfRange(bytes, 0, length));
    }
}

class Input {
    public String article;
}

class Output {
    public String[] dictionary;
}

class Process {
    public Output process(final Input input) {
        final Output output = new Output();

        final Set<String> dictionary = new TreeSet<>();
        final String article = input.article;

        int left = 0;
        while(left < article.length()) {
            while (true) {
                final boolean isEOF = left >= article.length();
                if (isEOF) break;

                final boolean isLetter = Character.isLetter(article.charAt(left));
                if (isLetter) break;

                final boolean isHyphen = article.charAt(left) == '-';
                if (isHyphen) break;

                left++;
            }
            if (left >= article.length()) break;

            int right = left;
            final StringBuilder word = new StringBuilder();
            while (true) {
                final boolean isEOF = right >= article.length();
                if (isEOF) break;

                final boolean isLetter = Character.isLetter(article.charAt(right));
                final boolean isHyphen = article.charAt(right) == '-';

                if (isLetter) {
                    word.append(Character.toLowerCase(article.charAt(right)));
                    right++;
                } else if (isHyphen) {
                    final boolean beforeNewLine = right + 1 < article.length() && article.charAt(right + 1) == '\n';
                    if (beforeNewLine) {
                        right += 2;
                    } else {
                        word.append(article.charAt(right));
                        right++;
                    }
                } else {
                    right++;
                    break;
                }
            }

            dictionary.add(word.toString());
            left = right;
        }

        output.dictionary = dictionary.toArray(new String[0]);
        return output;
    }
}
