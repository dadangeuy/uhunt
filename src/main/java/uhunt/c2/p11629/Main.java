package uhunt.c2.p11629;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class Main {
    private static BufferedReader in;
    private static String[] lines;
    private static StringBuilder out;

    public static void main(String... args) throws IOException {
        in = new BufferedReader(new InputStreamReader(System.in));
        out = new StringBuilder();

        lines = in.readLine().split(" ", 2);
        int totalParty = Integer.parseInt(lines[0]);
        int totalGuess = Integer.parseInt(lines[1]);

        Map<String, Integer> votePerParty = new HashMap<>(totalParty * 2);
        for (int i = 0; i < totalParty; i++) {
            lines = in.readLine().split(" ", 2);
            String party = lines[0];
            int vote = Integer.parseInt(lines[1].replace(".", ""));
            votePerParty.put(party, vote);
        }

        for (int i = 0; i < totalGuess; i++) {
            String guess = in.readLine();

            Solution solution = new Solution(votePerParty, guess);
            boolean isCorrect = solution.isCorrect();
            out.append(String.format("Guess #%d was %s.\n", i + 1, isCorrect ? "correct" : "incorrect"));
        }

        System.out.print(out);
    }
}

// split guess by space
// last guess was target number
// 2nd last guess was operator(<, >, <=, >=, =)
// sum first-to-3rd-last, compare to target number
// multiply vote & target by 10, so we can use integer comparator instead of decimal
class Solution {
    private final Map<String, Integer> votePerParty;
    private final String guess;

    public Solution(Map<String, Integer> votePerParty, String guess) {
        this.votePerParty = votePerParty;
        this.guess = guess;
    }

    public boolean isCorrect() {
        String[] values = guess.split(" ");

        double sum = 0;
        for (int i = 0; i < values.length - 2; i += 2) {
            double vote = votePerParty.get(values[i]);
            sum += vote;
        }

        String operator = values[values.length - 2];
        int target = Integer.parseInt(values[values.length - 1]) * 10;

        if (operator.equals("<")) return sum < target;
        if (operator.equals(">")) return sum > target;
        if (operator.equals("<=")) return sum <= target;
        if (operator.equals(">=")) return sum >= target;
        if (operator.equals("=")) return sum == target;
        return false;
    }
}
