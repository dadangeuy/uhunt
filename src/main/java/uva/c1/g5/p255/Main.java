package uva.c1.g5.p255;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {
    public static void main(String... args) {
        Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 16));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 16));

        while (in.hasNextInt()) {
            int kingPlace = in.nextInt();
            int queenSrcPlace = in.nextInt();
            int queenDstPlace = in.nextInt();

            Solution solution = new Solution(kingPlace, queenSrcPlace, queenDstPlace);
            String state = solution.getBoardState();

            out.println(state);
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Solution {
    private static final String ILLEGAL_STATE = "Illegal state";
    private static final String ILLEGAL_MOVE = "Illegal move";
    private static final String MOVE_NOT_ALLOWED = "Move not allowed";
    private static final String CONTINUE = "Continue";
    private static final String STOP = "Stop";
    private static final int[][] KING_MOVES = new int[][]{
        new int[]{-1, 0},
        new int[]{1, 0},
        new int[]{0, -1},
        new int[]{0, 1}
    };

    private final int kingPlace;
    private final int queenSrcPlace;
    private final int queenDstPlace;

    public Solution(int kingPlace, int queenSrcPlace, int queenDstPlace) {
        this.kingPlace = kingPlace;
        this.queenSrcPlace = queenSrcPlace;
        this.queenDstPlace = queenDstPlace;
    }

    public String getBoardState() {
        if (isIllegalState()) return ILLEGAL_STATE;
        if (isIllegalMove()) return ILLEGAL_MOVE;
        if (isMoveNotAllowed()) return MOVE_NOT_ALLOWED;
        if (isContinue()) return CONTINUE;
        return STOP;
    }

    private boolean isIllegalState() {
        return kingPlace == queenSrcPlace;
    }

    private boolean isIllegalMove() {
        // case 1: check if queen position changed
        boolean hasMove = queenSrcPlace != queenDstPlace;
        if (!hasMove) return true;

        // case 2: check if queen move in horizontal / vertical direction
        boolean moveHorizontal = row(queenSrcPlace) == row(queenDstPlace);
        boolean moveVertical = col(queenSrcPlace) == col(queenDstPlace);
        boolean hasValidDirection = (moveHorizontal || moveVertical);
        if (!hasValidDirection) return true;

        // case 3: check if queen go through king
        int frow = Math.min(row(queenSrcPlace), row(queenDstPlace));
        int urow = Math.max(row(queenSrcPlace), row(queenDstPlace));
        int fcol = Math.min(col(queenSrcPlace), col(queenDstPlace));
        int ucol = Math.max(col(queenSrcPlace), col(queenDstPlace));
        for (int row = frow; row <= urow; row++) {
            for (int col = fcol; col <= ucol; col++) {
                boolean meetKing = place(row, col) == kingPlace;
                if (meetKing) return true;
            }
        }

        return false;
    }

    private boolean isMoveNotAllowed() {
        return overlapWithKing(queenDstPlace, kingPlace);
    }

    private boolean isContinue() {
        // check if king has any move that didn't overlap with queen
        for (int[] move : KING_MOVES) {
            int kingDstRow = row(kingPlace) + move[0];
            int kingDstCol = col(kingPlace) + move[1];
            int kingDstPlace = place(kingDstRow, kingDstCol);

            if (!valid(kingDstRow, kingDstCol)) continue;
            if (overlapWithQueen(kingDstPlace, queenDstPlace)) continue;

            return true;
        }

        return false;
    }

    private boolean overlapWithKing(int queenPlace, int kingPlace) {
        boolean sameRow = row(kingPlace) == row(queenPlace);
        boolean sameCol = col(kingPlace) == col(queenPlace);

        boolean overlapLeft = sameRow && col(queenPlace) == col(kingPlace) - 1;
        boolean overlapRight = sameRow && col(queenPlace) == col(kingPlace) + 1;
        boolean overlapUp = sameCol && row(queenPlace) == row(kingPlace) - 1;
        boolean overlapDown = sameCol && row(queenPlace) == row(kingPlace) + 1;

        return overlapLeft || overlapRight || overlapUp || overlapDown;
    }

    private boolean overlapWithQueen(int kingPlace, int queenPlace) {
        boolean overlapHorizontally = row(kingPlace) == row(queenPlace);
        boolean overlapVertically = col(kingPlace) == col(queenPlace);
        return overlapHorizontally || overlapVertically;
    }

    private int row(int place) {
        return place / 8;
    }

    private int col(int place) {
        return place % 8;
    }

    private int place(int row, int col) {
        return row * 8 + col;
    }

    private boolean valid(int row, int col) {
        return 0 <= row && row < 8 && 0 <= col && col < 8;
    }
}
