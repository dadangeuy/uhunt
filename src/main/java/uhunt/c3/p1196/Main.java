package uhunt.c3.p1196;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

/**
 * 1196 - Tiling Up Blocks
 * Time limit: 3.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&page=show_problem&problem=3637
 */
public class Main {
    public static void main(final String... args) {
        final Scanner in = new Scanner(new BufferedInputStream(System.in));
        final PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));
        final Process process = new Process();

        while (in.hasNextInt()) {
            final Input input = new Input();

            input.totalBlocks = in.nextInt();
            final boolean isEOF = input.totalBlocks == 0;
            if (isEOF) break;

            input.blocks = new int[input.totalBlocks][];
            for (int i = 0; i < input.totalBlocks; i++) {
                final int[] block = new int[]{in.nextInt(), in.nextInt()};
                input.blocks[i] = block;
            }

            final Output output = process.process(input);
            out.println(output.tallestTilingBlocks);
        }
        out.println('*');

        in.close();
        out.flush();
        out.close();
    }
}

class Input {
    public int totalBlocks;
    public int[][] blocks;
}

class Output {
    public int tallestTilingBlocks;
}

class Process {
    public Output process(final Input input) {
        final Output output = new Output();
        output.tallestTilingBlocks = 0;

        final Block[] blocks = Arrays.stream(input.blocks)
            .map(b -> new Block(b[0], b[1]))
            .sorted(Block.compareByLeftAndMiddle)
            .toArray(Block[]::new);

        final int[] counts = new int[blocks.length];
        Arrays.fill(counts, 1);

        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < i; j++) {
                if (combinable(blocks[j], blocks[i])) {
                    counts[i] = Math.max(counts[i], counts[j] + 1);
                }
            }
        }

        output.tallestTilingBlocks = Arrays.stream(counts).max().orElse(0);
        return output;
    }

    private boolean combinable(final Block bottom, final Block top) {
        return bottom.left <= top.left && bottom.middle <= top.middle;
    }
}

class Block {
    public static Comparator<Block> compareByLeftAndMiddle = Comparator
        .comparingInt((Block b) -> b.left)
        .thenComparingInt((Block b) -> b.middle);
    public final int left;
    public final int middle;

    public Block(final int left, final int middle) {
        this.left = left;
        this.middle = middle;
    }
}
