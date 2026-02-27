package uva.uhunt.c1.g2.p10942;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.Optional;
import java.util.Scanner;

/**
 * 10942 - Can of Beans
 * Time limit: 3.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=1883
 */
public class Main {
    public static void main(String... args) {
        Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 16));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 16));

        int totalCase = in.nextInt();
        for (int i = 0; i < totalCase; i++) {
            long century = in.nextLong();
            long[] unorderedExpiredDate = new long[3];
            unorderedExpiredDate[0] = in.nextLong();
            unorderedExpiredDate[1] = in.nextLong();
            unorderedExpiredDate[2] = in.nextLong();

            Solution solution = new Solution(century, unorderedExpiredDate);
            Optional<long[]> opEarliestExpiredDate = solution.getEarliestExpiredDate();

            if (opEarliestExpiredDate.isPresent()) {
                long[] earliestExpiredDate = opEarliestExpiredDate.get();
                out.format(
                    "%02d %02d %02d\n",
                    earliestExpiredDate[0] % 100,
                    earliestExpiredDate[1],
                    earliestExpiredDate[2]
                );
            } else {
                out.println(-1);
            }
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Solution {
    private static final long[] months29 = new long[]{2};
    private static final long[] months30 = new long[]{4, 6, 9, 11};
    private static final long[] months31 = new long[]{1, 3, 5, 7, 8, 10, 12};

    private final long century;
    private final long[] unorderedExpiredDate;

    public Solution(long century, long[] unorderedExpiredDate) {
        this.century = century;
        this.unorderedExpiredDate = unorderedExpiredDate;
    }

    public Optional<long[]> getEarliestExpiredDate() {
        long[] earliestExpiredDate = null;

        for (int i = 0; i < 3; i++) {
            long day = unorderedExpiredDate[i];

            for (int j = 0; j < 3; j++) {
                if (i == j) continue;
                long month = unorderedExpiredDate[j];

                for (int k = 0; k < 3; k++) {
                    if (i == k || j == k) continue;
                    long year = century * 100 + unorderedExpiredDate[k];

                    long[] expiredDate = new long[]{year, month, day};
                    if (!validDate(expiredDate)) continue;

                    boolean earlier = earliestExpiredDate == null || isAfter(earliestExpiredDate, expiredDate);
                    if (earlier) earliestExpiredDate = expiredDate;
                }
            }
        }

        return Optional.ofNullable(earliestExpiredDate);
    }

    private boolean validDate(long[] date) {
        long year = date[0], month = date[1], day = date[2];

        boolean validMonth = between(1, month, 12);
        if (!validMonth) return false;

        boolean validDay = contains(month, months29) ? between(1, day, 29)
            : contains(month, months30) ? between(1, day, 30)
            : contains(month, months31) && between(1, day, 31);
        if (!validDay) return false;

        boolean leapYear = (year % 400 == 0) || (year % 100 != 0 && year % 4 == 0);
        boolean leapDate = month == 2 && day == 29;
        return !leapDate || leapYear;
    }

    private boolean isAfter(long[] dateAfter, long[] dateBefore) {
        if (dateAfter[0] == dateBefore[0]) {
            if (dateAfter[1] == dateBefore[1]) {
                return dateAfter[2] > dateBefore[2];
            }
            return dateAfter[1] > dateBefore[1];
        }
        return dateAfter[0] > dateBefore[0];
    }

    private boolean between(long left, long middle, long right) {
        return left <= middle && middle <= right;
    }

    private boolean contains(long target, long[] array) {
        for (long value : array)
            if (value == target)
                return true;
        return false;
    }
}
