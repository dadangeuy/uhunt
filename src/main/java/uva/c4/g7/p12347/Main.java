package uva.c4.g7.p12347;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

/**
 * 12347 - Binary Search Tree
 * Time limit: 1.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=3769
 */
public class Main {
    public static void main(final String... args) throws IOException {
        final BufferedReader in = new BufferedReader(new InputStreamReader(System.in), 1 << 8);
        final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out), 1 << 8);
        final Process process = new Process();

        final List<Integer> numbers = new LinkedList<>();
        for (String line = readLine(in); line != null; line = in.readLine()) {
            final int number = Integer.parseInt(line);
            numbers.add(number);
        }
        final int[] arrayNumbers = numbers.stream().mapToInt(i -> i).toArray();

        final Input input = new Input(arrayNumbers);
        final Output output = process.process(input);

        for (int number : output.postOrderTraversal) {
            out.write(Integer.toString(number));
            out.write('\n');
        }

        in.close();
        out.flush();
        out.close();
    }

    private static String readLine(final BufferedReader in) throws IOException {
        String line = in.readLine();
        while (line != null && line.isEmpty()) line = in.readLine();
        return line;
    }
}

class Input {
    public final int[] preOrderTraversal;

    public Input(final int[] preOrderTraversal) {
        this.preOrderTraversal = preOrderTraversal;
    }
}

class Output {
    public final int[] postOrderTraversal;

    public Output(final int[] postOrderTraversal) {
        this.postOrderTraversal = postOrderTraversal;
    }
}

class Process {
    public Output process(final Input input) {
        final Queue<Integer> preOrderTraversal = Arrays.stream(input.preOrderTraversal)
            .boxed()
            .collect(Collectors.toCollection(LinkedList::new));
        final BinarySearchTree root = createBinarySearchTree(
            preOrderTraversal,
            Integer.MIN_VALUE,
            Integer.MAX_VALUE
        );
        final int[] postOrderTraversal = postOrderTraversal(root);

        return new Output(postOrderTraversal);
    }

    private BinarySearchTree createBinarySearchTree(
        final Queue<Integer> preOrderTraversal,
        final int minimumValue,
        final int maximumValue
    ) {
        if (preOrderTraversal.isEmpty()) return null;

        final int value = preOrderTraversal.peek();
        final boolean isValid = minimumValue <= value && value <= maximumValue;
        if (!isValid) return null;

        preOrderTraversal.remove();

        final BinarySearchTree root = new BinarySearchTree();
        root.value = value;
        root.left = createBinarySearchTree(preOrderTraversal, minimumValue, value);
        root.right = createBinarySearchTree(preOrderTraversal, value, maximumValue);

        return root;
    }

    private int[] postOrderTraversal(
        final BinarySearchTree root
    ) {
        final LinkedList<Integer> list = postOrderTraversal(root, new LinkedList<>());
        return list.stream().mapToInt(i -> i).toArray();
    }

    private LinkedList<Integer> postOrderTraversal(
        final BinarySearchTree root,
        final LinkedList<Integer> traversal
    ) {
        if (root.left != null) {
            postOrderTraversal(root.left, traversal);
        }
        if (root.right != null) {
            postOrderTraversal(root.right, traversal);
        }
        traversal.addLast(root.value);
        return traversal;
    }
}

class BinarySearchTree {
    public Integer value;
    public BinarySearchTree left;
    public BinarySearchTree right;
}
