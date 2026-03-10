package uva.uhunt.c3.g8.p1738;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 1738 - Ceiling Function
 * Time limit: 10.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=5005
 */
public class Main {
    public static void main(final String... args) {
        final Scanner in = new Scanner(new BufferedInputStream(System.in));
        final PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));
        final Process process = new Process();

        while (in.hasNextInt()) {
            final Input input = new Input();
            input.totalCeilings = in.nextInt();
            input.totalLayers = in.nextInt();
            input.ceilings = new int[input.totalCeilings][input.totalLayers];
            for (int i = 0; i < input.totalCeilings; i++) {
                for (int j = 0; j < input.totalLayers; j++) {
                    input.ceilings[i][j] = in.nextInt();
                }
            }

            final Output output = process.process(input);
            out.println(output.totalShapes);
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Input {
    public int totalCeilings;
    public int totalLayers;
    public int[][] ceilings;
}

class Output {
    public int totalShapes;
}

class Process {
    public Output process(final Input input) {
        final Set<String> shapes = new HashSet<>();
        for (final int[] ceiling : input.ceilings) {
            final Tree tree = new Tree();
            for (final int layer : ceiling) {
                tree.add(layer);
            }
            final String shape = tree.getShape();
            shapes.add(shape);
        }

        final Output output = new Output();
        output.totalShapes = shapes.size();
        return output;
    }
}

class Tree {
    public Node root;

    public void add(final int value) {
        root = add(root, value);
    }

    private Node add(final Node node, final int value) {
        if (node == null) return new Node(value);

        if (value < node.value) node.left = add(node.left, value);
        else node.right = add(node.right, value);
        return node;
    }

    public String getShape() {
        final LinkedList<String> path = new LinkedList<>();
        inorderTraversal(root, 0, 0, path);
        final String shape = path.stream()
            .map(Object::toString)
            .collect(Collectors.joining("_"));
        return shape;
    }

    private void inorderTraversal(
        final Node node,
        final int totalLefts,
        final int totalRights,
        final LinkedList<String> path
    ) {
        if (node != null) {
            inorderTraversal(node.left, totalLefts + 1, totalRights, path);
            path.addLast(String.format("%d:%d", totalLefts, totalRights));
            inorderTraversal(node.right, totalLefts, totalRights + 1, path);
        }
    }
}

class Node {
    public final int value;
    public Node left;
    public Node right;

    public Node(final int value) {
        this.value = value;
    }
}
