package uhunt.template;

public final class FenwickTree {
    private final int length;
    private final int[] sumTree;

    public FenwickTree(int length) {
        this(new int[length], length);
    }

    public FenwickTree(int[] sumTree, int length) {
        this.sumTree = sumTree;
        this.length = sumTree.length;
    }

    public int length() {
        return length;
    }

    public int get(int index) {
        int result = sumTree[index];
        // For each consecutive 1 in the lowest order bits of index
        for (int i = 1; (index & i) != 0; i <<= 1)
            result -= sumTree[index ^ i];
        return result;
    }

    public void set(int index, long val) {
        add(index, val - get(index));
    }

    public void add(int index, long delta) {
        while (index < length()) {
            sumTree[index] += delta;
            index |= index + 1;  // Set lowest 0 bit; strictly increasing
            // Equivalently: index |= Integer.lowestOneBit(~index);
        }
    }

    public int getTotal() {
        return getPrefixSum(length());
    }

    /**
     * @param end - end of range, exclusive
     * @return range sum from 0 to end
     */
    public int getPrefixSum(int end) {
        int result = 0;
        while (end > 0) {
            result += sumTree[end - 1];
            end &= end - 1;  // Clear lowest 1 bit; strictly decreasing
            // Equivalently: end ^= Integer.lowestOneBit(end);
        }
        return result;
    }


    /**
     * @param start - start of range, inclusive
     * @param end   - end of range, exclusive
     * @return range sum from start to end
     */
    public int getRangeSum(int start, int end) {
        return getPrefixSum(end) - getPrefixSum(start);
    }
}
