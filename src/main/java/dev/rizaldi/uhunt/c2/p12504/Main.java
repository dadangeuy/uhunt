package dev.rizaldi.uhunt.c2.p12504;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 12504 - Updating a Dictionary
 * Time limit: 1.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=3948
 */
public class Main {

    public static void main(String... args) {
        Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 16));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 16));

        int totalTest = in.nextInt();
        for (int i = 0; i < totalTest; i++) {
            String oldDictionary = in.next();
            String newDictionary = in.next();

            Solution solution = new Solution(oldDictionary, newDictionary);
            String[][] changes = solution.getChanges();

            boolean empty = changes[0].length == 0 && changes[1].length == 0 && changes[2].length == 0;
            if (empty) {
                out.println("No changes");
            } else {
                for (int j = 0; j < 3; j++) {
                    if (changes[j].length == 0) continue;
                    out.print(j == 0 ? '+' : j == 1 ? '-' : '*');
                    out.println(String.join(",", changes[j]));
                }
            }
            out.println();
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Solution {
    private static final Pattern KEY_VALUE_PATTERN = Pattern.compile("[a-z]+:[0-9]+");
    private final String oldDictionary;
    private final String newDictionary;

    public Solution(String oldDictionary, String newDictionary) {
        this.oldDictionary = oldDictionary;
        this.newDictionary = newDictionary;
    }

    public String[][] getChanges() {
        TreeMap<String, String> oldMap = toMap(oldDictionary);
        TreeMap<String, String> newMap = toMap(newDictionary);

        String[] addedKeys = findAddedKeys(oldMap, newMap);
        String[] removedKeys = findRemovedKeys(oldMap, newMap);
        String[] modifiedKeys = findModifiedKeys(oldMap, newMap);

        return new String[][]{addedKeys, removedKeys, modifiedKeys};
    }

    private TreeMap<String, String> toMap(String dictionary) {
        TreeMap<String, String> map = new TreeMap<>();

        Matcher matcher = KEY_VALUE_PATTERN.matcher(dictionary);
        while (matcher.find()) {
            String[] pair = matcher.group().split(":");
            String key = pair[0];
            String value = pair[1];
            map.put(key, value);
        }
        return map;
    }

    private String[] findAddedKeys(TreeMap<String, String> oldMap, TreeMap<String, String> newMap) {
        return newMap.keySet().stream().filter(k -> !oldMap.containsKey(k)).toArray(String[]::new);
    }

    private String[] findRemovedKeys(TreeMap<String, String> oldMap, TreeMap<String, String> newMap) {
        return oldMap.keySet().stream().filter(k -> !newMap.containsKey(k)).toArray(String[]::new);
    }

    private String[] findModifiedKeys(TreeMap<String, String> oldMap, TreeMap<String, String> newMap) {
        return oldMap.keySet().stream().filter(newMap::containsKey).filter(k -> !oldMap.get(k).equals(newMap.get(k))).toArray(String[]::new);
    }
}
