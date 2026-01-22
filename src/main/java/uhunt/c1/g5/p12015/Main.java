package uhunt.c1.g5.p12015;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String... args) {
        Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 16));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 16));

        String[] urls = new String[10];
        int[] relevances = new int[10];

        int totalCase = in.nextInt();
        for (int i = 0; i < totalCase; i++) {
            for (int j = 0; j < 10; j++) {
                urls[j] = in.next();
                relevances[j] = in.nextInt();
            }

            List<String> relevantUrls = getMostRelevantUrls(urls, relevances);

            out.format("Case #%d:\n", i + 1);
            relevantUrls.forEach(out::println);
        }

        in.close();
        out.flush();
        out.close();
    }

    private static List<String> getMostRelevantUrls(String[] urls, int[] relevances) {
        int maxRelevance = max(relevances);

        LinkedList<String> relevantUrls = new LinkedList<>();
        for (int i = 0; i < 10; i++)
            if (relevances[i] == maxRelevance)
                relevantUrls.add(urls[i]);

        return relevantUrls;
    }

    private static int max(int[] array) {
        int max = array[0];
        for (int value : array) max = Math.max(max, value);
        return max;
    }
}
