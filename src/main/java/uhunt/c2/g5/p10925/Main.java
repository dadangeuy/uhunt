package uhunt.c2.g5.p10925;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.Scanner;

public class Main {
    public static void main(String... args) {
        Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 16));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 16));

        int caseNum = 1;
        while (true) {
            int totalItem = in.nextInt();
            BigInteger totalFriend = in.nextBigInteger();

            if (totalItem == 0 && totalFriend.equals(BigInteger.ZERO)) break;

            BigInteger totalBill = BigInteger.ZERO;
            for (int i = 0; i < totalItem; i++) {
                BigInteger bill = in.nextBigInteger();
                totalBill = totalBill.add(bill);
            }

            BigInteger totalBillPerFriend = totalBill.divide(totalFriend);
            out.format("Bill #%d costs %s: each friend should pay %s\n\n", caseNum++, totalBill, totalBillPerFriend);
        }

        in.close();
        out.flush();
        out.close();
    }
}
