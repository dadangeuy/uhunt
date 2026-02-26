package uva.c3.g6.p10026;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * 10026 - Shoemaker's Problem
 * Time limit: 3.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&page=show_problem&problem=967
 */
public class Main {
    public static void main(final String... args) throws IOException {
        final BufferedReader in = new BufferedReader(new InputStreamReader(System.in), 1 << 8);
        final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out), 1 << 8);
        final Process process = new Process();

        final int totalCases = Integer.parseInt(readLine(in));
        for (int i = 0; i < totalCases; i++) {
            final int totalJobs = Integer.parseInt(readLine(in));
            final int[][] jobs = new int[totalJobs][];
            for (int j = 0; j < totalJobs; j++) {
                final String[] l1 = readSplitLine(in);
                final int totalDays = Integer.parseInt(l1[0]);
                final int dailyFine = Integer.parseInt(l1[1]);
                final int[] job = new int[]{totalDays, dailyFine};
                jobs[j] = job;
            }

            final Input input = new Input(totalJobs, jobs);
            final Output output = process.process(input);

            if (i > 0) out.write('\n');
            for (int j = 0; j < output.jobIds.length; j++) {
                final int jobId = output.jobIds[j];
                if (j < output.jobIds.length - 1) out.write(String.format("%d ", jobId));
                else out.write(String.format("%d\n", jobId));
            }
        }

        in.close();
        out.flush();
        out.close();
    }

    private static String[] readSplitLine(final BufferedReader in) throws IOException {
        final String line = readLine(in);
        return line == null ? null : line.split(" ");
    }

    private static String readLine(final BufferedReader in) throws IOException {
        String line = in.readLine();
        while (line != null && line.isEmpty()) line = in.readLine();
        return line;
    }
}

class Input {
    public final int totalJobs;
    public final int[][] jobs;

    public Input(final int totalJobs, final int[][] jobs) {
        this.totalJobs = totalJobs;
        this.jobs = jobs;
    }
}

class Output {
    public final int[] jobIds;

    public Output(final int[] jobIds) {
        this.jobIds = jobIds;
    }
}

class Process {
    public Output process(final Input input) {
        final List<Job> jobs = new ArrayList<>(input.totalJobs);
        for (int i = 0; i < input.totalJobs; i++) {
            final int[] raw = input.jobs[i];
            final Job job = new Job(i + 1, raw[0], raw[1]);
            jobs.add(job);
        }

        final int[] jobIds = jobs.stream()
            .sorted(Job.ORDER_BY_RATIO)
            .mapToInt(j -> j.id)
            .toArray();

        return new Output(jobIds);
    }
}

class Job {
    public static final Comparator<Job> ORDER_BY_RATIO = Comparator
        .comparingDouble((Job job) -> (double) job.totalDays / (double) job.dailyFine)
        .thenComparingInt((Job j) -> j.id);

    public final int id;
    public final int totalDays;
    public final int dailyFine;

    public Job(final int id, final int totalDays, final int dailyFine) {
        this.id = id;
        this.totalDays = totalDays;
        this.dailyFine = dailyFine;
    }
}
