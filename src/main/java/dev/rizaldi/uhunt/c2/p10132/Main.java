package dev.rizaldi.uhunt.c2.p10132;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 10132 - File Fragmentation
 * Time limit: 3.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=1073
 */
public class Main {
    public static void main(String... args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

        int totalTestCases = Integer.parseInt(in.readLine());
        in.readLine();
        for (int i = 0; i < totalTestCases; i++) {
            List<String> fragments = new ArrayList<>();
            while (true) {
                String file = in.readLine();
                if (file == null || file.isEmpty()) break;
                fragments.add(file);
            }

            if (i > 0) out.append('\n');
            String originalFile = recoverFile(fragments);
            out.append(originalFile).append('\n');
        }

        in.close();
        out.flush();
        out.close();
    }

    private static String recoverFile(List<String> fragments) {
        Map<String, Integer> countPerCombination = new HashMap<>();

        for (int i = 0; i < fragments.size(); i++) {
            for (int j = i + 1; j < fragments.size(); j++) {
                String combination1 = fragments.get(i) + fragments.get(j);
                String combination2 = fragments.get(j) + fragments.get(i);

                countPerCombination.put(combination1, countPerCombination.getOrDefault(combination1, 0) + 1);
                if (!combination1.equals(combination2)) {
                    countPerCombination.put(combination2, countPerCombination.getOrDefault(combination2, 0) + 1);
                }
            }
        }

        return countPerCombination.entrySet().stream()
                .max(Comparator.comparingInt(Entry::getValue))
                .map(Entry::getKey)
                .orElse("");
    }
}
