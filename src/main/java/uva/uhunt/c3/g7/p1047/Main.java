package uva.uhunt.c3.g7.p1047;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.IntStream;

/**
 * 1047 - Zones
 * Time limit: 3.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=3488
 */
public class Main {
    public static void main(final String... args) {
        final Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 8));
        final PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 8));
        final Process process = new Process();

        for (int caseNumber = 1; in.hasNextInt(); caseNumber++) {
            final int totalTowersPlanned = in.nextInt();
            final int totalTowersBuilt = in.nextInt();

            final boolean isEOF = totalTowersPlanned == 0 && totalTowersBuilt == 0;
            if (isEOF) break;

            final Tower[] towers = new Tower[totalTowersPlanned];
            for (int towerId = 1; towerId <= totalTowersPlanned; towerId++) {
                final int totalCustomers = in.nextInt();
                final Tower tower = new Tower(towerId, totalCustomers);
                towers[towerId - 1] = tower;
            }

            final int totalCommonServiceAreas = in.nextInt();

            final CommonServiceArea[] commonServiceAreas = new CommonServiceArea[totalCommonServiceAreas];
            for (int csaId = 1; csaId <= totalCommonServiceAreas; csaId++) {
                final int csaTotalTowers = in.nextInt();

                final Tower[] csaTowers = new Tower[csaTotalTowers];
                for (int i = 0; i < csaTotalTowers; i++) {
                    final int csaTowerId = in.nextInt();
                    final Tower csaTower = towers[csaTowerId - 1];
                    csaTowers[i] = csaTower;
                }

                final int csaTotalCustomers = in.nextInt();

                final CommonServiceArea csa = new CommonServiceArea(
                    csaId,
                    csaTotalTowers,
                    csaTowers,
                    csaTotalCustomers
                );

                commonServiceAreas[csaId - 1] = csa;
            }

            final Input input = new Input(
                caseNumber,
                totalTowersPlanned,
                totalTowersBuilt,
                towers,
                totalCommonServiceAreas,
                commonServiceAreas
            );
            final Output output = process.process(input);

            out.format("Case Number  %d\n", output.caseNumber);
            out.format("Number of Customers: %d\n", output.numberOfCustomers);
            out.write("Locations recommended:");
            for (final int location : output.locationsRecommended) out.format(" %d", location);
            out.write("\n\n");
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Input {
    public final int caseNumber;
    // totalTowersPlanned <= 20
    public final int totalTowersPlanned;
    // totalTowersBuilt <= totalTowersPlanned
    public final int totalTowersBuilt;
    public final Tower[] towers;
    // totalCommonServiceAreas <= 10
    public final int totalCommonServiceAreas;
    public final CommonServiceArea[] commonServiceAreas;

    public Input(
        final int caseNumber,
        final int totalTowersPlanned,
        final int totalTowersBuilt,
        final Tower[] towers,
        final int totalCommonServiceAreas,
        final CommonServiceArea[] commonServiceAreas
    ) {
        this.caseNumber = caseNumber;
        this.totalTowersPlanned = totalTowersPlanned;
        this.totalTowersBuilt = totalTowersBuilt;
        this.towers = towers;
        this.totalCommonServiceAreas = totalCommonServiceAreas;
        this.commonServiceAreas = commonServiceAreas;
    }
}

class Output {
    public final int caseNumber;
    public final int numberOfCustomers;
    public final int[] locationsRecommended;

    public Output(
        final int caseNumber,
        final int numberOfCustomers,
        final int[] locationsRecommended
    ) {
        this.caseNumber = caseNumber;
        this.numberOfCustomers = numberOfCustomers;
        this.locationsRecommended = locationsRecommended;
    }
}

class Process {
    public Output process(final Input input) {
        final Map<Integer, Integer> totalCustomersPerState = new HashMap<>();

        for (final CommonServiceArea area : input.commonServiceAreas) {
            totalCustomersPerState.put(
                area.bitmask,
                area.totalCustomers + totalCustomersPerState.getOrDefault(area.bitmask, 0)
            );
        }

        for (final Tower tower : input.towers) {
            int totalCustomers = tower.totalCustomers;
            for (final CommonServiceArea area : input.commonServiceAreas) {
                final boolean notOverlap = (area.bitmask & tower.bitmask) == 0;
                if (notOverlap) continue;
                totalCustomers -= area.totalCustomers;
            }
            totalCustomersPerState.put(
                tower.bitmask,
                totalCustomers + totalCustomersPerState.getOrDefault(tower.bitmask, 0)
            );
        }

        final int maxState = (1 << (input.totalTowersPlanned)) - 1;
        final int[] endStates = IntStream
            .rangeClosed(1, maxState)
            .filter(s -> count(s) == input.totalTowersBuilt)
            .toArray();

        int maxEndTotalCustomers = 0;
        int maxEndState = 0;
        int[] maxTowerIds = null;

        for (final int endState : endStates) {
            int endTotalCustomers = 0;

            for (Map.Entry<Integer, Integer> entry : totalCustomersPerState.entrySet()) {
                final int state = entry.getKey();
                final int totalCustomers = entry.getValue();

                final boolean notOverlap = (endState & state) == 0;
                if (notOverlap) continue;

                endTotalCustomers += totalCustomers;
            }

            if (endTotalCustomers > maxEndTotalCustomers) {
                maxEndTotalCustomers = endTotalCustomers;
                maxEndState = endState;
                maxTowerIds = toTowerIds(maxEndState);
            } else if (endTotalCustomers == maxEndTotalCustomers && isBetterState(maxEndState, endState)) {
                maxEndState = endState;
                maxTowerIds = toTowerIds(maxEndState);
            }
        }

        return new Output(input.caseNumber, maxEndTotalCustomers, maxTowerIds);
    }

    private int count(int state) {
        int count = 0;
        while (state > 0) {
            count += state & 1;
            state >>= 1;
        }
        return count;
    }

    private int[] toTowerIds(int state) {
        final int[] towerIds = new int[count(state)];
        for (int towerId = 1, i = 0; state > 0; towerId++, state >>= 1) {
            if ((state & 1) == 0) continue;
            towerIds[i++] = towerId;
        }
        return towerIds;
    }

    private boolean isBetterState(int oldState, int newState) {
        while (oldState > 0 || newState > 0) {
            if ((oldState & 1) != (newState & 1)) {
                return (newState & 1) == 1;
            }

            oldState >>= 1;
            newState >>= 1;
        }

        return false;
    }
}

class Tower {
    public final int id;
    public final int totalCustomers;

    public final int index;
    public final int bitmask;

    public Tower(
        final int id,
        final int totalCustomers
    ) {
        this.id = id;
        this.totalCustomers = totalCustomers;

        this.index = getIndex();
        this.bitmask = getBitmask();
    }

    private int getIndex() {
        return id - 1;
    }

    private int getBitmask() {
        return (1 << index);
    }
}

class CommonServiceArea {
    public final int id;
    public final int totalTowers;
    public final Tower[] towers;
    public final int totalCustomers;

    public final int index;
    public final int bitmask;

    public CommonServiceArea(
        final int id,
        final int totalTowers,
        final Tower[] towers,
        final int totalCustomers
    ) {
        this.id = id;
        this.totalTowers = totalTowers;
        this.towers = towers;
        this.totalCustomers = totalCustomers;

        this.index = getIndex();
        this.bitmask = getBitmask();
    }

    private int getIndex() {
        return id - 1;
    }

    private int getBitmask() {
        int bitmask = 0;
        for (final Tower tower : towers) {
            final int index = tower.index;
            bitmask |= (1 << index);
        }
        return bitmask;
    }
}
