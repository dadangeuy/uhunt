package uhunt.c1.g8.p12478;

import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 12478 - Hardest Problem Ever (Easy)
 * Time limit: 1.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=3922#google_vignette
 */
public class Main {
    public static void main(String... args) {
        final PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 16));

        final Solution solution = new Solution();
        final String name = solution.findHiddenName();
        out.println(name);

        out.flush();
        out.close();
    }
}

class Solution {
    private final String[] names = new String[]{
            "RAKIBUL",
            "ANINDYA",
            "MOSHIUR",
            "SHIPLU",
            "KABIR",
            "SUNNY",
            "OBAIDA",
            "WASI",
    };
    private final char[][] grid = new char[][]{
            "OBIDAIBKR".toCharArray(),
            "RKAULHISP".toCharArray(),
            "SADIYANNO".toCharArray(),
            "HEISAWHIA".toCharArray(),
            "IRAKIBULS".toCharArray(),
            "MFBINTRNO".toCharArray(),
            "UTOYZIFAH".toCharArray(),
            "LEBSYNUNE".toCharArray(),
            "EMOTIONAL".toCharArray(),
    };
    private final int totalRows = grid.length;
    private final int totalColumns = grid[0].length;

    public String findHiddenName() {
        return "KABIR";
    }

    private String doFindHiddenName() {
        final Map<String, String> namePerSortedName = new HashMap<>(2 * names.length);
        for (final String name : names) {
            namePerSortedName.put(sort(name), name);
        }

        final Map<String, Integer> countPerSortedName = new HashMap<>(2 * names.length);
        for (final String name : names) {
            countPerSortedName.put(sort(name), 0);
        }

        // find horizontally
        for (int i = 0; i < totalRows; i++) {
            for (int j = 0; j < totalColumns; j++) {
                final StringBuilder hiddenNameBuilder = new StringBuilder();
                for (int k = j; k < totalColumns; k++) {
                    hiddenNameBuilder.append(grid[i][k]);
                    countPerSortedName.computeIfPresent(sort(hiddenNameBuilder.toString()), (key, value) -> value + 1);
                }
            }
        }

        // find vertically
        for (int i = 0; i < totalColumns; i++) {
            for (int j = 0; j < totalRows; j++) {
                final StringBuilder hiddenNameBuilder = new StringBuilder();
                for (int k = j; k < totalRows; k++) {
                    hiddenNameBuilder.append(grid[k][i]);
                    countPerSortedName.computeIfPresent(sort(hiddenNameBuilder.toString()), (key, value) -> value + 1);
                }
            }
        }

        return countPerSortedName.entrySet().stream()
                .filter(entry -> entry.getValue() == 2)
                .findFirst()
                .map(Map.Entry::getKey)
                .map(namePerSortedName::get)
                .orElseThrow(NullPointerException::new);
    }

    private String sort(String text) {
        char[] characters = text.toCharArray();
        Arrays.sort(characters);
        return new String(characters);
    }
}
