package uva.uhunt.c2.g3.p673;

import java.util.LinkedList;
import java.util.Scanner;

public class Main {
    public static void main(String... args) {
        Scanner in = new Scanner(System.in);
        int totalTest = in.nextInt();
        in.nextLine();

        for (int test = 0; test < totalTest; test++) {
            String text = in.nextLine();
            Solution solution = new Solution(text);
            boolean isCorrect = solution.isCorrect();
            if (isCorrect) System.out.println("Yes");
            else System.out.println("No");
        }
    }
}

class Solution {
    private final String text;

    public Solution(String text) {
        this.text = text;
    }

    public boolean isCorrect() {
        LinkedList<Character> letterq = new LinkedList<>();
        for (char letter : text.toCharArray()) {
            if (letter == '(' || letter == '[') {
                letterq.addFirst(letter);
            } else if (letter == ')') {
                if (!letterq.isEmpty() && letterq.getFirst().equals('(')) letterq.removeFirst();
                else return false;
            } else if (letter == ']') {
                if (!letterq.isEmpty() && letterq.getFirst().equals('[')) letterq.removeFirst();
                else return false;
            }
        }

        return letterq.isEmpty();
    }
}
