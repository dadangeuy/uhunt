package uhunt.c4.g0.p200;

import java.util.*;

public class Main {
    public static void main(String... args) {
        Scanner in = new Scanner(System.in);
        List<String> texts = new LinkedList<>();

        while (in.hasNext()) {
            texts.clear();
            String text;
            while (!(text = in.next()).equals("#")) texts.add(text);

            Solution solution = new Solution(texts);
            String sequence = solution.findCollatingSequence();
            System.out.println(sequence);
        }
    }
}

class Pair<A, B> {
    public final A first;
    public final B second;

    public Pair(A first, B second) {
        this.first = first;
        this.second = second;
    }
}

class Solution {
    private final String[] texts;

    public Solution(Collection<String> collectionTexts) {
        this.texts = collectionTexts.toArray(new String[0]);
    }

    public String findCollatingSequence() {
        Pair<Map<Character, Set<Character>>, Map<Character, Integer>> pair = buildGraph();
        Map<Character, Set<Character>> sequenceGraph = pair.first;
        Map<Character, Integer> requirements = pair.second;

        LinkedList<Character> sequenceq = new LinkedList<>();
        for (Map.Entry<Character, Integer> entry : requirements.entrySet()) {
            char letter = entry.getKey();
            int requirement = entry.getValue();
            if (requirement == 0) sequenceq.addLast(letter);
        }

        StringBuilder sequence = new StringBuilder();
        while (!sequenceq.isEmpty()) {
            char letter = sequenceq.removeFirst();

            // remove requirements
            for (char nextLetter : sequenceGraph.get(letter)) {
                int requirement = requirements.get(nextLetter) - 1;
                requirements.put(nextLetter, requirement);
                if (requirement == 0) sequenceq.addLast(nextLetter);
            }

            sequence.append(letter);
        }

        return sequence.toString();
    }

    private Pair<Map<Character, Set<Character>>, Map<Character, Integer>> buildGraph() {
        Map<Character, Set<Character>> sequenceGraph = new HashMap<>(26 * 2);
        Map<Character, Integer> requirements = new HashMap<>(26 * 2);

        Set<Character> letters = getAllLetters();
        for (char letter : letters) {
            sequenceGraph.put(letter, new HashSet<>(26 * 2));
            requirements.put(letter, 0);
        }

        for (int i = 0, j = 1; j < texts.length; i++, j++) {
            String texti = texts[i];
            String textj = texts[j];

            int countMatch = countMatch(texti, textj);
            if (countMatch == texti.length() || countMatch == textj.length()) continue;

            // sequence graph: i -> j
            char letteri = texti.charAt(countMatch);
            char letterj = textj.charAt(countMatch);

            if (sequenceGraph.get(letteri).contains(letterj)) continue;
            sequenceGraph.get(letteri).add(letterj);
            requirements.put(letterj, requirements.get(letterj) + 1);
        }

        return new Pair<>(sequenceGraph, requirements);
    }

    private int countMatch(String a, String b) {
        int index = 0;
        while (index < a.length() && index < b.length() && a.charAt(index) == b.charAt(index)) ++index;
        return index;
    }

    private Set<Character> getAllLetters() {
        Set<Character> letters = new HashSet<>(26 * 2);
        for (String text : texts) {
            for (char c : text.toCharArray()) {
                letters.add(c);
            }
        }
        return letters;
    }
}
