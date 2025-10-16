package uhunt.c1.p12157;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {
    public static void main(String... args) {
        Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 16));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 16));

        int totalCase = in.nextInt();
        for (int i = 0; i < totalCase; i++) {
            int totalCall = in.nextInt();
            int[] durations = new int[totalCall];
            for (int j = 0; j < totalCall; j++) durations[j] = in.nextInt();

            int mileCost = mileCost(durations);
            int juiceCost = juiceCost(durations);

            if (mileCost < juiceCost) out.format("Case %d: Mile %d\n", i + 1, mileCost);
            else if (juiceCost < mileCost) out.format("Case %d: Juice %d\n", i + 1, juiceCost);
            else out.format("Case %d: Mile Juice %d\n", i + 1, mileCost);
        }

        in.close();
        out.flush();
        out.close();
    }

    private static int mileCost(int[] durations) {
        int totalCost = 0;
        for (int duration : durations) {
            int cost = cost(duration, 30, 10);
            totalCost += cost;
        }
        return totalCost;
    }

    private static int juiceCost(int[] durations) {
        int totalCost = 0;
        for (int duration : durations) {
            int cost = cost(duration, 60, 15);
            totalCost += cost;
        }
        return totalCost;
    }

    private static int cost(int duration, int rateDuration, int rateCost) {
        return rateCost + duration / rateDuration * rateCost;
    }
}
