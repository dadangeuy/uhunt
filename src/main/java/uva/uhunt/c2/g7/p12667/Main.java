package uva.uhunt.c2.g7.p12667;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

/**
 * 12667 - Last Blood
 * Time limit: 1.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=4405
 */
public class Main {
    public static void main(String... args) {
        Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 16));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 16));

        int totalProblem = in.nextInt();
        int totalTeam = in.nextInt();
        int totalSubmission = in.nextInt();

        Submission[] submissions = new Submission[totalSubmission];
        for (int i = 0; i < totalSubmission; i++) {
            int time = in.nextInt();
            int teamId = in.nextInt();
            char problem = in.next().charAt(0);
            String verdict = in.next();

            submissions[i] = new Submission(time, teamId, problem, verdict);
        }

        Solution solution = new Solution(totalProblem, totalTeam, totalSubmission, submissions);
        Submission[] lastSubmissions = solution.findLastSubmissions();

        for (int i = 0; i < totalProblem; i++) {
            Submission submission = lastSubmissions[i];
            if (submission == null) out.format("%c - -\n", 'A' + i);
            else out.format("%c %d %d\n", submission.problem, submission.time, submission.teamId);
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Submission {
    public final int time;
    public final int teamId;
    public final char problem;
    public final String verdict;

    public Submission(int time, int teamId, char problem, String verdict) {
        this.time = time;
        this.teamId = teamId;
        this.problem = problem;
        this.verdict = verdict;
    }

    public boolean isAccepted() {
        return verdict.equalsIgnoreCase("yes");
    }

    public int problemId() {
        return problem - 'A';
    }

    public int teamIdx() {
        return teamId - 1;
    }
}

class Solution {
    private static final Comparator<Submission> SORT_BY_TIME = Comparator.comparingInt(s -> s.time);
    private final int totalProblem;
    private final int totalTeam;
    private final int totalSubmission;
    private final Submission[] submissions;

    public Solution(int totalProblem, int totalTeam, int totalSubmission, Submission[] submissions) {
        this.totalProblem = totalProblem;
        this.totalTeam = totalTeam;
        this.totalSubmission = totalSubmission;
        this.submissions = submissions;
    }

    public Submission[] findLastSubmissions() {
        Arrays.sort(submissions, SORT_BY_TIME);

        boolean[][] hasSubmissions = new boolean[totalTeam][totalProblem];
        Submission[] lastSubmissions = new Submission[totalProblem];
        for (Submission submission : submissions) {
            if (!submission.isAccepted()) continue;
            if (submission.problemId() >= totalProblem) continue;
            if (hasSubmissions[submission.teamIdx()][submission.problemId()]) continue;

            hasSubmissions[submission.teamIdx()][submission.problemId()] = true;
            lastSubmissions[submission.problemId()] = submission;
        }

        return lastSubmissions;
    }
}
