package uhunt.c2.p13055;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;

public class Main {
    private static BufferedReader in;
    private static StringBuilder out;

    public static void main(String... args) throws IOException {
        in = new BufferedReader(new InputStreamReader(System.in));
        out = new StringBuilder();

        Solution solution = new Solution();

        int totalQuery = Integer.parseInt(in.readLine());
        for (int i = 0; i < totalQuery; i++) {
            String[] query = in.readLine().split(" ");

            if (query[0].equals("Sleep")) solution.sleep(query[1]);
            else if (query[0].equals("Kick")) solution.kick();
            else out.append(solution.test()).append('\n');
        }

        System.out.print(out);
    }
}

class Solution {
    private final LinkedList<String> dreamq;

    public Solution() {
        dreamq = new LinkedList<>();
    }

    public void sleep(String dream) {
        dreamq.addFirst(dream);
    }

    public void kick() {
        dreamq.pollFirst();
    }

    public String test() {
        return dreamq.isEmpty() ? "Not in a dream" : dreamq.getFirst();
    }
}
