package uhunt.c3.p12190;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * 12190 - Electric Bill
 * Time limit: 3.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=3342
 */
public class Main {
    public static void main(final String... args) {
        final Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 8));
        final PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 8));
        final Process process = new Process();

        while (true) {
            final Input input = new Input();
            input.mergedTotalPayments = in.nextLong();
            input.paymentDifference = in.nextLong();

            if (input.isEOF()) break;

            final Output output = process.process(input);
            out.write(Long.toString(output.myTotalPayments));
            out.write('\n');
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Input {
    // the total amount to pay if your consumptions were billed together;
    public long mergedTotalPayments;
    // he absolute value of the difference between the amounts of your bills
    public long paymentDifference;

    public boolean isEOF() {
        return mergedTotalPayments == 0 && paymentDifference == 0;
    }
}

class Output {
    // the amount you have to pay
    public long myTotalPayments;
}

class Process {
    private static final PriceRange[] listPriceRange = new PriceRange[]{
        new PriceRange(1, 100, 2),
        new PriceRange(101, 10000, 3),
        new PriceRange(10001, 1000000, 5),
        new PriceRange(1000001, Long.MAX_VALUE, 7),
    };

    public Output process(final Input input) {
        final Output output = new Output();

        final long totalConsumptions = getTotalConsumptions(input.mergedTotalPayments);
        final long myTotalPayments = getMyTotalPayments(totalConsumptions, input.paymentDifference);

        output.myTotalPayments = myTotalPayments;
        return output;
    }

    private long getTotalConsumptions(final long mergedTotalPayments) {
        long minimumTotalConsumptions = 0;
        long maximumTotalConsumptions = Long.MAX_VALUE;

        while (minimumTotalConsumptions < maximumTotalConsumptions) {
            final long middleTotalConsumptions = (minimumTotalConsumptions + maximumTotalConsumptions) / 2;
            final long middleTotalPayments = getTotalPayments(middleTotalConsumptions);

            if (mergedTotalPayments < middleTotalPayments) {
                maximumTotalConsumptions = middleTotalConsumptions - 1;
            } else if (mergedTotalPayments > middleTotalPayments) {
                minimumTotalConsumptions = middleTotalConsumptions + 1;
            } else {
                minimumTotalConsumptions = maximumTotalConsumptions = middleTotalConsumptions;
            }
        }

        return minimumTotalConsumptions;
    }

    private long getMyTotalPayments(final long totalConsumptions, final long paymentDifference) {
        long minimumMyTotalConsumptions = 0;
        long maximumMyTotalConsumptions = totalConsumptions / 2;

        while (minimumMyTotalConsumptions < maximumMyTotalConsumptions) {
            final long middleMyTotalConsumptions = (minimumMyTotalConsumptions + maximumMyTotalConsumptions) / 2;
            final long middleMyTotalPayments = getTotalPayments(middleMyTotalConsumptions);

            final long middleOtherTotalConsumptions = totalConsumptions - middleMyTotalConsumptions;
            final long middleOtherTotalPayments = getTotalPayments(middleOtherTotalConsumptions);

            final long middlePaymentDifference = middleOtherTotalPayments - middleMyTotalPayments;
            if (paymentDifference < middlePaymentDifference) {
                minimumMyTotalConsumptions = middleMyTotalConsumptions + 1;
            } else if (paymentDifference > middlePaymentDifference) {
                maximumMyTotalConsumptions = middleMyTotalConsumptions - 1;
            } else {
                minimumMyTotalConsumptions = maximumMyTotalConsumptions = middleMyTotalConsumptions;
            }
        }

        return getTotalPayments(minimumMyTotalConsumptions);
    }

    private long getTotalPayments(final long totalConsumptions) {
        long totalPayments = 0;
        long remainingTotalConsumptions = totalConsumptions;

        for (final PriceRange priceRange : listPriceRange) {
            final long maximumTotalConsumptions = priceRange.getMaximumTotalConsumptions();
            final long actualTotalConsumptions = Math.min(remainingTotalConsumptions, maximumTotalConsumptions);

            totalPayments += actualTotalConsumptions * priceRange.price;
            remainingTotalConsumptions -= actualTotalConsumptions;
        }

        return totalPayments;
    }
}

class PriceRange {
    public final long fromTotalConsumptions;
    public final long untilTotalConsumptions;
    public final long price;

    public PriceRange(final long fromTotalConsumptions, final long untilTotalConsumptions, final long price) {
        this.fromTotalConsumptions = fromTotalConsumptions;
        this.untilTotalConsumptions = untilTotalConsumptions;
        this.price = price;
    }

    public final long getMaximumTotalConsumptions() {
        return untilTotalConsumptions - fromTotalConsumptions + 1;
    }
}
