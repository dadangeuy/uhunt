package uhunt.c4.g9.p439;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;

public class Main {
    public static void main(String... args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String line;
        String[] lines;

        while ((line = in.readLine()) != null && !line.isEmpty()) {
            lines = line.split(" ");
            String source = lines[0];
            String destination = lines[1];

            Solution solution = new Solution(source, destination);
            int move = solution.getTotalMove();

            System.out.format("To get from %s to %s takes %d knight moves.\n", source, destination, move);
        }
    }
}

// use 2d array to keep track of number of step needed to reach a cell
// do bfs, starting from source, until we reach the destination
class Solution {
    private static final int[][] board = new int[8][8];
    private static final int[][] moves = new int[][]{
            new int[]{-1, -2},
            new int[]{-1, +2},
            new int[]{+1, -2},
            new int[]{+1, +2},
            new int[]{-2, -1},
            new int[]{-2, +1},
            new int[]{+2, -1},
            new int[]{+2, +1},
    };
    private final String source;
    private final String destination;

    public Solution(String source, String destination) {
        this.source = source;
        this.destination = destination;
    }

    public int getTotalMove() {
        fill(board, -1);

        int[] pSource = position(source);
        int[] pDestination = position(destination);

        LinkedList<int[]> positionq = new LinkedList<>();
        positionq.addLast(pSource);
        board[pSource[0]][pSource[1]] = 0;

        while (!positionq.isEmpty() && board[pDestination[0]][pDestination[1]] == -1) {
            int[] current = positionq.removeFirst();
            int step = board[current[0]][current[1]];

            for (int[] move : moves) {
                int[] next = new int[]{current[0] + move[0], current[1] + move[1]};
                if (!valid(next)) continue;
                if (board[next[0]][next[1]] != -1) continue;

                positionq.addLast(next);
                board[next[0]][next[1]] = step + 1;
            }
        }

        return board[pDestination[0]][pDestination[1]];
    }

    private void fill(int[][] arr, int value) {
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++)
                arr[i][j] = value;
    }

    private int[] position(String cell) {
        return new int[]{cell.charAt(0) - 'a', cell.charAt(1) - '1'};
    }

    private boolean valid(int[] pos) {
        return within(pos[0], 0, 7) && within(pos[1], 0, 7);
    }

    private boolean within(int value, int from, int to) {
        return from <= value && value <= to;
    }
}
