package uhunt.c2.g5.p10815;

import java.util.Scanner;
import java.util.TreeSet;

public class Main {
    public static void main(String... args) {
        Scanner in = new Scanner(System.in);
        TreeSet<String> dictionaries = new TreeSet<>();
        while (in.hasNextLine()) {
            String dirtyWord = in.nextLine();
            String[] cleanWords = dirtyWord.toLowerCase().split("[^a-z]+");
            for (String cleanWord : cleanWords) {
                if (cleanWord.length() == 0) continue;
                dictionaries.add(cleanWord);
            }
        }

        for (String word : dictionaries) System.out.println(word);
    }
}
