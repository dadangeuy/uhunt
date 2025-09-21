package dev.rizaldi.uhunt.c2.p10909;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.LinkedList;
import java.util.Random;

/**
 * 10909 - Lucky Number
 * Time limit: 3.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=1850
 */
public class Main {
    public static void main(final String... args) throws IOException {
        final BufferedReader in = new BufferedReader(new InputStreamReader(System.in), 1 << 16);
        final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out), 1 << 16);
        final Process process = new Process();

        while (true) {
            final String line = Util.readLine(in);
            if (line == null) break;

            final int number = Integer.parseInt(line);
            final Input input = new Input(number);

            final Output output = process.process(input);
            if (output.luckyFactors == null) {
                out.write(String.format(
                        "%d is not the sum of two luckies!\n",
                        output.number
                ));
            } else {
                out.write(String.format(
                        "%d is the sum of %d and %d.\n",
                        output.number, output.luckyFactors[0], output.luckyFactors[1]
                ));
            }
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Util {
    public static String readLine(final BufferedReader in) throws IOException {
        String line = in.readLine();
        while (line != null && line.isEmpty()) line = in.readLine();
        return line;
    }
}

class Input {
    public final int number;

    public Input(final int number) {
        this.number = number;
    }
}

class Output {
    public final int number;
    public final int[] luckyFactors;

    public Output(final int number, final int[] luckyFactors) {
        this.number = number;
        this.luckyFactors = luckyFactors;
    }
}

class Process {
    private static final int[] LUCKY_NUMBERS = generateLuckyNumbers(2_000_000);
    private static final boolean[] IS_LUCKY_NUMBERS = generateIsLuckyNumbers(LUCKY_NUMBERS, 2_000_000);

    public Output process(final Input input) {
        final boolean isOdd = (input.number & 1) == 1;
        if (isOdd) return new Output(input.number, null);

        final int lteIndex = binarySearchLTE(LUCKY_NUMBERS, input.number / 2);
        for (int i = lteIndex; i >= 0; i--) {
            final int factor1 = LUCKY_NUMBERS[i];
            final int factor2 = input.number - factor1;

            final boolean isValid = IS_LUCKY_NUMBERS[factor2];
            if (isValid) return new Output(input.number, new int[]{factor1, factor2});
        }

        return new Output(input.number, null);
    }

    private int binarySearchLTE(final int[] array, final int number) {
        int left = 0, right = array.length - 1;
        while (left < right) {
            final int middle = (left + right + 1) / 2;
            if (array[middle] > number) {
                right = middle - 1;
            } else if (array[middle] <= number) {
                left = middle;
            }
        }
        return left;
    }

    private static boolean[] generateIsLuckyNumbers(final int[] luckyNumbers, final int max) {
        final boolean[] isLuckyNumbers = new boolean[max + 1];
        for (int luckyNumber : luckyNumbers) isLuckyNumbers[luckyNumber] = true;
        return isLuckyNumbers;
    }

    private static int[] generateLuckyNumbers(final int max) {
        final OrderStatisticTreap treap = new OrderStatisticTreap();

        // odd values
        for (int i = 1; i <= max; i += 2) treap.insert(i);

        // i-th values elimination
        for (int i = 1; i < treap.size(); i++) {
            final int interval = treap.select(i);
            final int lastIndex = ((treap.size() / interval) * interval) - 1;
            if (lastIndex < 0) break;

            for (int j = lastIndex; j >= 0; j -= interval) {
                final int key = treap.select(j);
                treap.remove(key);
            }
        }

        return treap.values();
    }
}

class OrderStatisticTreap {
    private Node root = null;

    public void insert(final int key) {
        root = insert(root, key);
    }

    private Node insert(Node node, final int key) {
        if (node == null) {
            return new Node(key);
        }

        if (key <= node.key) {
            node.left = insert(node.left, key);
            if (node.left.priority > node.priority) {
                node = rotateRight(node);
            }
        } else {
            node.right = insert(node.right, key);
            if (node.right.priority > node.priority) {
                node = rotateLeft(node);
            }
        }
        node.updateSize();

        return node;
    }

    private Node rotateLeft(final Node node) {
        final Node right = node.right;
        node.right = right.left;
        right.left = node;

        return right;
    }

    private Node rotateRight(final Node node) {
        final Node left = node.left;
        node.left = left.right;
        left.right = node;

        return left;
    }

    public void remove(final int key) {
        root = remove(root, key);
    }

    private Node remove(Node node, final int key) {
        if (node == null) {
            return null;
        }

        if (key < node.key) {
            node.left = remove(node.left, key);
        } else if (node.key < key) {
            node.right = remove(node.right, key);
        } else {
            if (node.left == null) {
                return node.right;
            } else if (node.right == null) {
                return node.left;
            } else {
                if (node.left.priority > node.right.priority) {
                    node = rotateRight(node);
                    node.right = remove(node.right, key);
                } else {
                    node = rotateLeft(node);
                    node.left = remove(node.left, key);
                }
            }
        }
        node.updateSize();

        return node;
    }

    public int select(final int index) {
        return select(root, root.leftSize(), index);
    }

    public int select(Node node, final int nodeIndex, final int targetIndex) {
        if (targetIndex < nodeIndex) {
            return select(node.left, nodeIndex - node.left.rightSize() - 1, targetIndex);
        } else if (nodeIndex < targetIndex) {
            return select(node.right, nodeIndex + node.right.leftSize() + 1, targetIndex);
        }
        return node.key;
    }

    public int[] values() {
        final LinkedList<Integer> result = new LinkedList<>();
        values(root, result);
        return result.stream().mapToInt(Integer::intValue).toArray();
    }

    private void values(Node node, final LinkedList<Integer> result) {
        if (node != null) {
            values(node.left, result);
            result.add(node.key);
            values(node.right, result);
        }
    }

    public int size() {
        return root.size;
    }
}

class Node {
    private static final Random random = new Random(0L);

    public final int key;
    public final int priority;
    public Node left;
    public Node right;
    public int size;

    public Node(final int key) {
        this.key = key;
        this.priority = random.nextInt();
        this.size = 1;
    }

    public void updateSize() {
        this.size = leftSize() + rightSize() + 1;
    }

    public int leftSize() {
        return left == null ? 0 : left.size;
    }

    public int rightSize() {
        return right == null ? 0 : right.size;
    }
}
