package uva.c1.g1.p13151;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {
    public static void main(String... args) {
        Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 16));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 16));

        while (true) {
            String ivalue = in.next();
            int totalStatement = in.nextInt();
            if (ivalue.equals("0") && totalStatement == 0) break;

            int value = toDecimal(ivalue);
            int totalMark = 0;
            for (int i = 0; i < totalStatement; i++) {
                String statement = in.next();
                int output = in.nextInt();

                boolean correct = false;
                if (statement.equals("i")) correct = value == output;
                else if (statement.equals("++i")) correct = output == ++value;
                else if (statement.equals("i++")) correct = output == value++;
                else if (statement.equals("--i")) correct = output == --value;
                else if (statement.equals("i--")) correct = output == value--;

                if (correct) totalMark++;
                else if (statement.equals("i++")) value = output + 1;
                else if (statement.equals("i--")) value = output - 1;
                else value = output;
            }

            out.println(totalMark);
        }

        in.close();
        out.flush();
        out.close();
    }

    private static int toDecimal(String ivalue) {
        boolean hexadecimal = ivalue.startsWith("0x");
        boolean octal = !hexadecimal && ivalue.startsWith("0");
        int base = hexadecimal ? 16 : octal ? 8 : 10;
        String cvalue = hexadecimal ? ivalue.substring(2) : octal ? ivalue.substring(1) : ivalue;

        return Integer.parseInt(cvalue, base);
    }
}
