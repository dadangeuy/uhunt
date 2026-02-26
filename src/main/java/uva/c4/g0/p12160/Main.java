package uva.c4.g0.p12160;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    public static void main(String... args) {
        Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 16));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 16));

        int caseNum = 0;
        while (in.hasNextInt()) {
            int lockCode = in.nextInt();
            int unlockCode = in.nextInt();
            int totalButton = in.nextInt();

            if (lockCode == 0 && unlockCode == 0 && totalButton == 0) break;

            int[] buttons = new int[totalButton];
            for (int i = 0; i < totalButton; i++) buttons[i] = in.nextInt();

            Solution solution = new Solution(lockCode, unlockCode, totalButton, buttons);
            Optional<Integer> press = solution.getMinimumPress();

            out.print("Case ");
            out.print(++caseNum);
            out.print(": ");

            if (press.isPresent()) out.println(press.get());
            else out.println("Permanently Locked");
        }

        in.close();
        out.flush();
        out.close();
    }
}

/**
 * 1) traverse every possible combination of lockCode + button[i], keep check of step to visit code
 * 2) use BFS algorithm to traverse the combination
 * 3) minimumPress = steps[unlockCode]
 * 4) time complexity: O(totalCode * totalButton) = O(9999 * 10)
 */
class Solution {
    private static final int CODE_LIMIT = 10_000;
    private static final int UNVISITED = Integer.MAX_VALUE;
    private static final int[] STEPS = new int[CODE_LIMIT];
    private final int lockCode;
    private final int unlockCode;
    private final int totalButton;
    private final int[] buttons;

    public Solution(int lockCode, int unlockCode, int totalButton, int[] buttons) {
        this.lockCode = lockCode;
        this.unlockCode = unlockCode;
        this.totalButton = totalButton;
        this.buttons = buttons;
    }

    public Optional<Integer> getMinimumPress() {
        Arrays.fill(STEPS, UNVISITED);

        LinkedList<Integer> codeq = new LinkedList<>();

        STEPS[lockCode] = 0;
        codeq.addLast(lockCode);

        while (!codeq.isEmpty()) {
            int code = codeq.removeFirst();
            int step = STEPS[code];

            for (int button : buttons) {
                int nextCode = (code + button) % CODE_LIMIT;
                int nextStep = step + 1;

                if (STEPS[nextCode] != UNVISITED) continue;

                STEPS[nextCode] = nextStep;
                codeq.addLast(nextCode);
            }
        }

        return STEPS[unlockCode] == UNVISITED ? Optional.empty() : Optional.of(STEPS[unlockCode]);
    }
}
