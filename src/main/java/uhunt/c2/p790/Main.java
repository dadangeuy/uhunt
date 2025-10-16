package uhunt.c2.p790;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.Collections.emptyList;
import static java.util.Collections.emptyMap;
import static java.util.stream.Collectors.groupingBy;

/**
 * 790 - Head Judge Headache
 * Time limit: 3.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=731
 */
public class Main {
    private static BufferedReader in;
    private static BufferedWriter out;

    public static void main(final String... args) throws IOException {
        in = new BufferedReader(new InputStreamReader(System.in), 1 << 16);
        out = new BufferedWriter(new OutputStreamWriter(System.out), 1 << 16);

        final int totalCases = Integer.parseInt(in.readLine());
        in.readLine();

        for (int i = 0; i < totalCases; i++) {
            final List<Submission> submissions = getSubmissions();
            final List<Performance> performances = getPerformances(submissions);

            if (i > 0) out.write('\n');
            out.write("RANK TEAM PRO/SOLVED TIME\n");
            for (final Performance performance : performances) {
                if (performance.totalSolved > 0) {
                    out.write(String.format(
                            "%4d %4d %4d       %4d\n",
                            performance.rank,
                            performance.teamId,
                            performance.totalSolved,
                            performance.totalTime
                    ));
                } else {
                    out.write(String.format(
                            "%4d %4d\n",
                            performance.rank,
                            performance.teamId
                    ));
                }
            }
        }

        in.close();
        out.flush();
        out.close();
    }

    private static List<Submission> getSubmissions() throws IOException {
        final List<Submission> submissions = new ArrayList<>();
        String line = in.readLine();

        while (line != null && !line.isEmpty()) {
            final String[] lines = line.split(" ");

            final int teamId = Integer.parseInt(lines[0]);
            final String problemId = lines[1];
            final int hour = Integer.parseInt(lines[2].split(":")[0]);
            final int minute = Integer.parseInt(lines[2].split(":")[1]);
            final String status = lines[3];

            final Submission submission = new Submission(teamId, problemId, hour * 60 + minute, status);
            submissions.add(submission);

            line = in.readLine();
        }

        return submissions;
    }

    private static List<Performance> getPerformances(final List<Submission> submissions) {
        // 1 - find unique teams
        final int maxTeam = submissions.stream()
                .mapToInt(s -> s.teamId)
                .max()
                .orElseThrow(NullPointerException::new);
        final int[] teams = IntStream.rangeClosed(1, maxTeam).toArray();

        // 2 - find unique problems
        final String[] problems = submissions.stream()
                .map(s -> s.problemId)
                .distinct()
                .sorted()
                .toArray(String[]::new);

        // 3 - group submissions by team id and problem id to optimize search
        final Map<Integer, Map<String, List<Submission>>> submissionsPerTeamAndProblem = submissions.stream()
                .sorted(Comparator.comparingInt((Submission s) -> s.time).thenComparing((Submission s) -> s.status))
                .collect(groupingBy(s -> s.teamId, groupingBy(s -> s.problemId)));

        // 4 - calculate total time and solved for each team
        final Map<Integer, Performance> performancePerTeam = new HashMap<>();
        for (final int team : teams) {
            performancePerTeam.put(team, new Performance(team));
        }

        for (int team : teams) {
            final Performance performance = performancePerTeam.get(team);

            for (String problem : problems) {
                final List<Submission> currentSubmissions = submissionsPerTeamAndProblem
                        .getOrDefault(team, emptyMap())
                        .getOrDefault(problem, emptyList());

                if (isSolved(currentSubmissions)) {
                    final int currentTotalTime = getTotalTime(currentSubmissions);
                    performance.totalTime += currentTotalTime;
                    performance.totalSolved += 1;
                }
            }
        }

        // 5 - sort performance by total solved DESC, time ASC, team ASC
        final List<Performance> performances = performancePerTeam.values().stream()
                .sorted(Comparator
                        .comparingInt((Performance p) -> p.totalSolved).reversed()
                        .thenComparingInt((Performance p) -> p.totalTime)
                        .thenComparingInt((Performance p) -> p.teamId))
                .collect(Collectors.toList());

        // 6 - update rank
        performances.get(0).rank = 1;
        for (int i = 1; i < performances.size(); i++) {
            final Performance prevPerformance = performances.get(i - 1);
            final Performance currPerformance = performances.get(i);

            final boolean equalSolved = currPerformance.totalSolved == prevPerformance.totalSolved;
            final boolean equalTime = currPerformance.totalTime == prevPerformance.totalTime;
            if (equalSolved && equalTime) {
                currPerformance.rank = prevPerformance.rank;
            } else {
                currPerformance.rank = i + 1;
            }
        }

        return performances;
    }

    private static boolean isSolved(List<Submission> submissions) {
        return submissions.stream().anyMatch(s -> s.status.equals("Y"));
    }

    private static int getTotalTime(List<Submission> submissions) {
        int penalty = 0;
        for (final Submission submission : submissions) {
            if (submission.status.equals("Y")) {
                return penalty + submission.time;
            } else {
                penalty += 20;
            }
        }
        return 0;
    }
}

class Performance {
    public final int teamId;
    public int rank = 1;
    public int totalSolved = 0;
    public int totalTime = 0;

    public Performance(final int teamId) {
        this.teamId = teamId;
    }
}

class Submission {
    public final int teamId;
    public final String problemId;
    public final int time;
    public final String status;

    public Submission(int teamId, String problemId, int time, String status) {
        this.teamId = teamId;
        this.problemId = problemId;
        this.time = time;
        this.status = status;
    }
}
