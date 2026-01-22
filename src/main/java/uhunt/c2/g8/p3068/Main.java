package uhunt.c2.g8.p3068;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * 11917 - Do Your Own Homework
 * Time limit: 1.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=3068
 */
public class Main {
    public static void main(String... args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

        int totalTestCases = Integer.parseInt(in.readLine());
        for (int i = 0; i < totalTestCases; i++) {
            int totalSubjects = Integer.parseInt(in.readLine());
            Map<String, Integer> daysPerSubject = new HashMap<>();
            for (int j = 0; j < totalSubjects; j++) {
                String[] texts = in.readLine().split(" ");
                String subject = texts[0];
                int days = Integer.parseInt(texts[1]);
                daysPerSubject.put(subject, days);
            }
            int remainingDays = Integer.parseInt(in.readLine());
            String dueSubject = in.readLine();

            int requiredDays = daysPerSubject.getOrDefault(dueSubject, Integer.MAX_VALUE);
            boolean isOnTime = requiredDays <= remainingDays;
            boolean isLate = !isOnTime && requiredDays <= remainingDays + 5;

            out
                    .append("Case ")
                    .append(String.valueOf(i + 1))
                    .append(": ")
                    .append(isOnTime? "Yesss" : isLate? "Late" : "Do your own homework!")
                    .append('\n');
        }

        in.close();
        out.flush();
        out.close();
    }
}
