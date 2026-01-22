package uhunt.c1.g0.p12250;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {
    public static void main(String... args) {
        Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 16));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 16));

        for (int i = 1; in.hasNext(); i++) {
            String greeting = in.next();
            if (greeting.equals("#")) break;

            String language = getLanguage(greeting);
            out.printf("Case %d: %s\n", i, language);
        }

        in.close();
        out.flush();
        out.close();
    }

    private static String getLanguage(String greeting) {
        switch (greeting) {
            case "HELLO":
                return "ENGLISH";
            case "HOLA":
                return "SPANISH";
            case "HALLO":
                return "GERMAN";
            case "BONJOUR":
                return "FRENCH";
            case "CIAO":
                return "ITALIAN";
            case "ZDRAVSTVUJTE":
                return "RUSSIAN";
            default:
                return "UNKNOWN";
        }
    }
}
