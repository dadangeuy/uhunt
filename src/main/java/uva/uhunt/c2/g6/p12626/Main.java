package uva.uhunt.c2.g6.p12626;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    private static BufferedReader in;
    private static StringBuilder out;

    public static void main(String... args) throws IOException {
        in = new BufferedReader(new InputStreamReader(System.in));
        out = new StringBuilder();

        int totalTest = Integer.parseInt(in.readLine());
        for (int i = 0; i < totalTest; i++) {
            char[] ingredients = in.readLine().toCharArray();
            Solution solution = new Solution(ingredients);
            int total = solution.getTotalMargarita();
            out.append(total).append('\n');
        }

        System.out.print(out);
    }
}

// count frequency of each letter, using DAT
// total possible margarita = min(count[M]/1, count[A]/3, count[R]/2, count[G]/1, count[I]/1, count[T]/1)
class Solution {
    private static final DirectAddressTable countPerLetter = new DirectAddressTable();
    private final char[] ingredients;

    public Solution(char[] ingredients) {
        this.ingredients = ingredients;
    }

    public int getTotalMargarita() {
        countPerLetter.clear();

        for (char ingredient : ingredients) {
            int count = countPerLetter.get(ingredient);
            countPerLetter.set(ingredient, count + 1);
        }

        int[] totals = new int[]{
            countPerLetter.get('M'),
            countPerLetter.get('A') / 3,
            countPerLetter.get('R') / 2,
            countPerLetter.get('G'),
            countPerLetter.get('I'),
            countPerLetter.get('T'),
        };

        return min(totals);
    }

    private int min(int[] arr) {
        int min = arr[0];
        for (int val : arr) min = Math.min(min, val);
        return min;
    }
}

class DirectAddressTable {
    private final int[] tables;

    public DirectAddressTable() {
        this.tables = new int[26];
    }

    public void set(char key, int value) {
        tables[address(key)] = value;
    }

    public int get(char key) {
        return tables[address(key)];
    }

    public void clear() {
        Arrays.fill(tables, 0);
    }

    private int address(char key) {
        return key - 'A';
    }
}
