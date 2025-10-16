package uhunt.c1.p11717;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Scanner;

enum State {
    INACTIVE, ACTIVATION, ACTIVE
}

/**
 * 11717 - Energy Saving Microcontroller
 * Time limit: 1.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&page=show_problem&problem=2764
 */
public class Main {
    public static void main(String... args) {
        Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 16));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 16));

        int totalScenario = in.nextInt();
        for (int i = 0; i < totalScenario; i++) {
            int totalInstruction = in.nextInt();
            int idlePeriod = in.nextInt();
            int activationPeriod = in.nextInt();

            int[] instructions = new int[totalInstruction];
            for (int j = 0; j < totalInstruction; j++) instructions[j] = in.nextInt();

            Solution solution = new Solution(idlePeriod, activationPeriod, instructions);
            int[] totalInactiveAndIgnored = solution.getTotalInactiveAndIgnored();

            int totalInactive = totalInactiveAndIgnored[0], totalIgnored = totalInactiveAndIgnored[1];
            out.format("Case %d: %d %d\n", i + 1, totalInactive, totalIgnored);
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Solution {
    private final int idlePeriod;
    private final int activationPeriod;
    private final int[] instructions;

    public Solution(int idlePeriod, int activationPeriod, int[] instructions) {
        this.idlePeriod = idlePeriod;
        this.activationPeriod = activationPeriod;
        this.instructions = instructions;
    }

    public int[] getTotalInactiveAndIgnored() {
        int totalInactive = 0, totalIgnored = 0;

        Controller controller = new Controller(idlePeriod, activationPeriod);
        for (int time : instructions) {
            boolean inactive = controller.getState(time).equals(State.INACTIVE);
            boolean executed = controller.sendInstruction(time);

            if (inactive) totalInactive++;
            if (!executed) totalIgnored++;
        }

        return new int[]{totalInactive, totalIgnored};
    }
}

/**
 * if active, execute immediately
 * if inactive, activate and process instruction on currentTime + activationPeriod
 * during activation, ignore instruction
 */
class Controller {
    private final int idlePeriod;
    private final int activationPeriod;
    private final LinkedList<Event<State>> events = new LinkedList<>();
    private State state;

    public Controller(int idlePeriod, int activationPeriod) {
        this.idlePeriod = idlePeriod;
        this.activationPeriod = activationPeriod;
        this.state = State.ACTIVE;

        this.events.addLast(new Event<>(idlePeriod, State.INACTIVE));
    }

    // true -> executed, false -> ignored
    public boolean sendInstruction(int currentTime) {
        consumeEvents(currentTime);

        if (state == State.INACTIVE) {
            events.addLast(new Event<>(currentTime, State.ACTIVATION));
            events.addLast(new Event<>(currentTime + activationPeriod, State.ACTIVE));
            events.addLast(new Event<>(currentTime + activationPeriod + idlePeriod, State.INACTIVE));
            return true;
        } else if (state == State.ACTIVATION) {
            return false;
        } else if (state == State.ACTIVE) {
            events.clear();
            events.addLast(new Event<>(currentTime + idlePeriod, State.INACTIVE));
            return true;
        }

        return false;
    }

    public State getState(int currentTime) {
        consumeEvents(currentTime);
        return state;
    }

    private void consumeEvents(int currentTime) {
        while (!events.isEmpty() && events.getFirst().canConsume(currentTime)) {
            Event<State> event = events.removeFirst();
            state = event.getMessage();
        }
    }

    private boolean pastEvent(Event<?> event, int currentTime) {
        return event.getCreatedAt() <= currentTime;
    }
}

class Event<T> {
    private final int createdAt;
    private final T message;

    public Event(int createdAt, T message) {
        this.createdAt = createdAt;
        this.message = message;
    }

    public int getCreatedAt() {
        return createdAt;
    }

    public T getMessage() {
        return message;
    }

    public boolean canConsume(int currentTime) {
        return createdAt <= currentTime;
    }
}
