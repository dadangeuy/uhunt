package uva.c3.g3.p1153;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * 1153 - Keep the Customer Satisfied
 * Time limit: 3.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=3594
 */
public class Main {
    public static void main(final String... args) {
        final Scanner in = new Scanner(new BufferedInputStream(System.in));
        final PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));
        final Process process = new Process();

        final int totalCases = in.nextInt();
        for (int i = 0; i < totalCases; i++) {
            final Input input = new Input();
            input.totalOrders = in.nextInt();
            input.totalSteelsPerOrder = new int[input.totalOrders];
            input.dueDatePerOrder = new int[input.totalOrders];
            for (int j = 0; j < input.totalOrders; j++) {
                input.totalSteelsPerOrder[j] = in.nextInt();
                input.dueDatePerOrder[j] = in.nextInt();
            }

            final Output output = process.process(input);
            if (i > 0) out.println();
            out.println(output.maximumTotalAcceptedOrders);
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Input {
    public int totalOrders;
    public int[] totalSteelsPerOrder;
    public int[] dueDatePerOrder;
}

class Output {
    public int maximumTotalAcceptedOrders;
}

class Process {
    public Output process(final Input input) {
        final List<Order> orders = getOrders(input);
        final List<Order> acceptedOrders = scheduleOrdersUsingMooreHodgsonAlgorithm(orders);

        final Output output = new Output();
        output.maximumTotalAcceptedOrders = acceptedOrders.size();

        return output;
    }

    private List<Order> getOrders(final Input input) {
        final List<Order> orders = new ArrayList<>(input.totalOrders);

        for (int i = 0; i < input.totalOrders; i++) {
            final Order order = new Order();
            order.id = i;
            order.totalSteels = input.totalSteelsPerOrder[i];
            order.dueDate = input.dueDatePerOrder[i];
            orders.add(order);
        }

        return orders;
    }

    private List<Order> scheduleOrdersUsingMooreHodgsonAlgorithm(final List<Order> orders) {
        final PriorityQueue<Order> pendingOrders = new PriorityQueue<>(Order.ORDER_BY_DUE_DATE_ASC);
        final PriorityQueue<Order> acceptedOrders = new PriorityQueue<>(Order.ORDER_BY_TOTAL_STEELS_DESC);

        pendingOrders.addAll(orders);
        int completionTime = 0;

        while (!pendingOrders.isEmpty()) {
            final Order pendingOrder = pendingOrders.remove();
            acceptedOrders.add(pendingOrder);
            completionTime += pendingOrder.totalSteels;

            while (completionTime > pendingOrder.dueDate) {
                final Order rejectedOrder = acceptedOrders.remove();
                completionTime -= rejectedOrder.totalSteels;
            }
        }

        return new ArrayList<>(acceptedOrders);
    }
}

class Order {
    public static final Comparator<Order> ORDER_BY_DUE_DATE_ASC = Comparator.comparingInt((Order o) -> o.dueDate);
    public static final Comparator<Order> ORDER_BY_TOTAL_STEELS_DESC = Comparator.comparingInt((Order o) -> -o.totalSteels);

    public int id;
    public int totalSteels;
    public int dueDate;
}
