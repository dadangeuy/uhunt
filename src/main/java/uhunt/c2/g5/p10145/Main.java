package uhunt.c2.g5.p10145;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * 10145 - Lock Manager
 * Time limit: 3.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=1086
 */
public class Main {
    public static void main(String... args) {
        Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 16));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 16));

        int totalCase = in.nextInt();
        for (int i = 0; i < totalCase; i++) {
            if (i > 0) out.println();

            LockManager manager = new LockManager();
            while (true) {
                char mode = in.next().charAt(0);
                if (mode == '#') break;

                int transactionId = in.nextInt();
                int item = in.nextInt();

                Operation operation = new Operation(mode, transactionId, item);
                String state = manager.acquireLock(operation);

                out.println(state);
            }
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Operation {
    public static final char SHARED_MODE = 'S';
    public static final char EXCLUSIVE_MODE = 'X';
    public final char mode;
    public final int transactionId;
    public final int item;

    public Operation(char mode, int transactionId, int item) {
        this.mode = mode;
        this.transactionId = transactionId;
        this.item = item;
    }

    public boolean isShared() {
        return mode == SHARED_MODE;
    }

    public boolean isExclusive() {
        return mode == EXCLUSIVE_MODE;
    }
}

class LockManager {
    public static final String LOCK_GRANTED = "GRANTED";
    public static final String LOCK_DENIED = "DENIED";
    public static final String LOCK_IGNORED = "IGNORED";
    private final Map<Integer, Integer> exclusiveLocks = new HashMap<>();  // item, transactionId
    private final Map<Integer, Set<Integer>> sharedLocks = new HashMap<>();  // item, [transactionId]
    private final Set<Integer> ignoredTransactionIds = new HashSet<>();  // [transactionId]

    public String acquireLock(Operation operation) {
        boolean ignored = ignoredTransactionIds.contains(operation.transactionId);
        if (ignored) return LOCK_IGNORED;

        return operation.isShared() ? acquireSharedLock(operation) : acquireExclusiveLock(operation);
    }

    private String acquireSharedLock(Operation o) {
        if (!hasSharedOwnership(o)) {
            ignoredTransactionIds.add(o.transactionId);
            return LOCK_DENIED;
        }

        sharedLocks.computeIfAbsent(o.item, (v) -> new HashSet<>()).add(o.transactionId);
        return LOCK_GRANTED;
    }

    private String acquireExclusiveLock(Operation o) {
        if (!hasExclusiveOwnership(o)) {
            ignoredTransactionIds.add(o.transactionId);
            return LOCK_DENIED;
        }

        exclusiveLocks.put(o.item, o.transactionId);
        return LOCK_GRANTED;
    }

    private boolean hasSharedOwnership(Operation o) {
        return exclusiveLocks.getOrDefault(o.item, o.transactionId).equals(o.transactionId);
    }

    private boolean hasExclusiveOwnership(Operation o) {
        if (!hasSharedOwnership(o)) return false;

        Set<Integer> owners = sharedLocks.getOrDefault(o.item, Collections.singleton(o.transactionId));
        return owners.size() == 1 && owners.contains(o.transactionId);
    }
}
