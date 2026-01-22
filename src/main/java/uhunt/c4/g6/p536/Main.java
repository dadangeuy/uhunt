package uhunt.c4.g6.p536;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * 536 - Tree Recovery
 * Time limit: 3.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&page=show_problem&problem=477
 */
public class Main {
    public static void main(final String... args) throws IOException {
        final BufferedReader in = new BufferedReader(new InputStreamReader(System.in), 1 << 16);
        final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out), 1 << 16);

        final Process process = new Process();
        while (true) {
            final String[] l = readSplitLine(in);
            if (l == null) break;

            final char[] preorder = l[0].toCharArray();
            final char[] inorder = l[1].toCharArray();
            final Input input = new Input(preorder, inorder);

            final Output output = process.process(input);
            out.write(output.postorder);
            out.write('\n');
        }

        in.close();
        out.flush();
        out.close();
    }

    private static String[] readSplitLine(final BufferedReader in) throws IOException {
        final String line = readLine(in);
        return line == null ? null : line.split(" ");
    }

    private static String readLine(final BufferedReader in) throws IOException {
        String line = in.readLine();
        while (line != null && line.isEmpty()) line = in.readLine();
        return line;
    }
}

class Input {
    public final char[] preorder;
    public final char[] inorder;

    public Input(final char[] preorder, final char[] inorder) {
        this.preorder = preorder;
        this.inorder = inorder;
    }
}

class Output {
    public final char[] postorder;

    public Output(final char[] postorder) {
        this.postorder = postorder;
    }
}

class Process {
    /**
     * preorder -> root, left, right
     * inorder -> left, root, right
     * postorder -> left, right, root
     */
    public Output process(final Input input) {
        final Tree root = rebuildTree(input.preorder, input.inorder, new Tree());
        final LinkedList<Character> postorder = getPostorder(root, new LinkedList<>());
        return new Output(toArray(postorder));
    }

    private Tree rebuildTree(
            final char[] preorder,
            final char[] inorder,
            final Tree root
    ) {
        final char rootValue = preorder[0];
        root.value = rootValue;

        final int splitIdx = indexOf(inorder, rootValue);

        final char[] leftInorder = Arrays.copyOfRange(inorder, 0, splitIdx);
        final char[] rightInorder = Arrays.copyOfRange(inorder, splitIdx + 1, inorder.length);

        final char[] leftPreorder = Arrays.copyOfRange(preorder, 1, 1 + leftInorder.length);
        final char[] rightPreorder = Arrays.copyOfRange(preorder, 1 + leftInorder.length, preorder.length);

        if (leftInorder.length > 0) {
            root.left = new Tree();
            rebuildTree(leftPreorder, leftInorder, root.left);
        }
        if (rightInorder.length > 0) {
            root.right = new Tree();
            rebuildTree(rightPreorder, rightInorder, root.right);
        }

        return root;
    }

    private int indexOf(final char[] inorder, final char value) {
        for (int i = 0; i < inorder.length; i++) {
            if (inorder[i] == value) {
                return i;
            }
        }
        return -1;
    }

    private LinkedList<Character> getPostorder(final Tree root, final LinkedList<Character> postorder) {
        if (root.left != null) {
            getPostorder(root.left, postorder);
        }
        if (root.right != null) {
            getPostorder(root.right, postorder);
        }
        postorder.add(root.value);

        return postorder;
    }

    private char[] toArray(final List<Character> list) {
        final char[] array = new char[list.size()];
        int i = 0;
        for (char c : list) array[i++] = c;
        return array;
    }
}

class Tree {
    public char value;
    public Tree left;
    public Tree right;
}
