package uva.uhunt.c2.g9.p10909;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Optional;
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
    private static final Treap LUCKY_NUMBERS = generateLuckyNumbers(2_000_000);

    public Output process(final Input input) {
        final boolean isOdd = (input.number & 1) == 1;
        if (isOdd) return new Output(input.number, null);

        Optional<Integer> optionalFirstFactor = LUCKY_NUMBERS.floor(input.number / 2);
        while (optionalFirstFactor.isPresent()) {
            final int firstFactor = optionalFirstFactor.get();
            final int secondFactor = input.number - firstFactor;
            final boolean isLucky = LUCKY_NUMBERS.contains(secondFactor);
            if (isLucky) {
                return new Output(input.number, new int[]{firstFactor, secondFactor});
            }
            optionalFirstFactor = LUCKY_NUMBERS.floor(firstFactor - 1);
        }

        return new Output(input.number, null);
    }

    private static Treap generateLuckyNumbers(final int max) {
        final Treap treap = new Treap();

        // add odd values
        for (int i = 1; i <= max; i += 2) treap.insert(i);

        // delete interval values
        for (int i = 1; i < treap.size(); i++) {
            final int interval = treap.findKey(i).orElseThrow(NullPointerException::new);
            final int lastIndex = ((treap.size() / interval) * interval) - 1;
            if (lastIndex < 0) break;

            for (int j = lastIndex; j >= 0; j -= interval) {
                final int key = treap.findKey(j).orElseThrow(NullPointerException::new);
                treap.delete(key);
            }
        }

        return treap;
    }
}

interface Tree {
    void insert(final int key);

    void delete(final int key);

    boolean contains(final int key);

    Optional<Integer> floor(final int key);
}

interface OrderStatisticTree {
    Optional<Integer> findKey(final int index);

    Optional<Integer> findIndex(final int key);
}

class Treap implements Tree, OrderStatisticTree {
    private Node root = null;

    @Override
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

    @Override
    public void delete(final int key) {
        root = delete(root, key);
    }

    private Node delete(Node node, final int key) {
        if (node == null) {
            return null;
        }

        if (key < node.key) {
            node.left = delete(node.left, key);
        } else if (node.key < key) {
            node.right = delete(node.right, key);
        } else {
            if (node.left == null) {
                return node.right;
            } else if (node.right == null) {
                return node.left;
            } else {
                if (node.left.priority > node.right.priority) {
                    node = rotateRight(node);
                    node.right = delete(node.right, key);
                } else {
                    node = rotateLeft(node);
                    node.left = delete(node.left, key);
                }
            }
        }
        node.updateSize();

        return node;
    }

    @Override
    public boolean contains(final int key) {
        return contains(root, key);
    }

    private boolean contains(final Node node, final int key) {
        if (node == null) {
            return false;
        } else if (node.key == key) {
            return true;
        } else if (key <= node.key) {
            return contains(node.left, key);
        } else {
            return contains(node.right, key);
        }
    }

    @Override
    public Optional<Integer> floor(final int key) {
        return floor(root, key);
    }

    private Optional<Integer> floor(final Node node, final int key) {
        if (node == null) {
            return Optional.empty();
        } else if (key < node.key) {
            return floor(node.left, key);
        } else if (node.key < key) {
            final Optional<Integer> right = floor(node.right, key);
            return right
                    .map(rightKey -> Optional.of(Math.max(rightKey, node.key)))
                    .orElseGet(() -> Optional.of(node.key));
        } else {
            return Optional.of(node.key);
        }
    }

    @Override
    public Optional<Integer> findKey(final int index) {
        return findKey(root, root.leftSize(), index);
    }

    @Override
    public Optional<Integer> findIndex(final int key) {
        return findIndex(root, root.leftSize(), key);
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

    public Optional<Integer> findKey(final Node node, final int nodeIndex, final int index) {
        if (node == null) {
            return Optional.empty();
        } else if (index < nodeIndex) {
            return findKey(node.left, nodeIndex - node.left.rightSize() - 1, index);
        } else if (nodeIndex < index) {
            return findKey(node.right, nodeIndex + node.right.leftSize() + 1, index);
        } else {
            return Optional.of(node.key);
        }
    }

    private Optional<Integer> findIndex(final Node node, final int nodeIndex, final int key) {
        if (node == null) {
            return Optional.empty();
        } else if (key < node.key) {
            return findIndex(node.left, nodeIndex - node.left.rightSize() - 1, key);
        } else if (node.key < key) {
            return findIndex(node.right, nodeIndex + node.right.leftSize() + 1, key);
        } else {
            return Optional.of(nodeIndex);
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
