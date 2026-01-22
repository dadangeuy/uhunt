package uhunt.c3.g4.p234;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Scanner;
import java.util.TreeSet;
import java.util.stream.Stream;

/**
 * 234 - Switching Channels
 * Time limit: 3.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&page=show_problem&problem=170
 */
public class Main {
    public static void main(final String... args) {
        final Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 8));
        final PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 8));
        final Process process = new Process();

        for (int dataSet = 1; in.hasNextInt(); dataSet++) {
            final int totalProgrammes = in.nextInt();
            final boolean isEOF = totalProgrammes == 0;
            if (isEOF) break;

            final Program[] programmes = new Program[totalProgrammes];
            for (int i = 0; i < totalProgrammes; i++) {
                final int length = in.nextInt();
                programmes[i] = new Program(i, length);
            }

            final int totalAlignments = in.nextInt();

            final Alignment[] alignments = new Alignment[totalAlignments];
            for (int i = 0; i < totalAlignments; i++) {
                final int importance = in.nextInt();
                final int time = in.nextInt();
                alignments[i] = new Alignment(i, importance, time);
            }

            final Input input = new Input(dataSet, totalProgrammes, programmes, totalAlignments, alignments);
            final Output output = process.process(input);

            out.format("Data set %d\nOrder:", output.dataSet);
            for (final Program program : output.order) out.format(" %d", program.length);
            out.format("\nError: %d\n", output.error);
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Constraint {
    public static final int TOTAL_PROGRAMMES_MAX = 8;
    public static final int TOTAL_ALIGNMENTS_MAX = 8;
    public static final int IMPORTANCE_MAX = 5;
}

class Input {
    public final int dataSet;
    public final int totalProgrammes;
    public final Program[] programmes;
    public final int totalAlignments;
    public final Alignment[] alignments;

    public Input(
        final int dataSet,
        final int totalProgrammes,
        final Program[] programmes,
        final int totalAlignments,
        final Alignment[] alignments
    ) {
        this.dataSet = dataSet;
        this.totalProgrammes = totalProgrammes;
        this.programmes = programmes;
        this.totalAlignments = totalAlignments;
        this.alignments = alignments;
    }
}

class Output {
    public final int dataSet;
    public final Program[] order;
    public final int error;

    public Output(
        final int dataSet,
        final Program[] order,
        final int error
    ) {
        this.dataSet = dataSet;
        this.order = order;
        this.error = error;
    }
}

class Process {
    public Output process(final Input input) {
        final Schedule schedule = permutations(
            input.programmes,
            input.alignments,
            new LinkedHashSet<>()
        );

        return new Output(input.dataSet, schedule.programmes, schedule.error.totalMisses());
    }

    public Schedule permutations(
        final Program[] programmes,
        final Alignment[] alignments,
        final LinkedHashSet<Program> scheduledProgrammes
    ) {
        if (scheduledProgrammes.size() == programmes.length) {
            final Program[] copy = scheduledProgrammes.toArray(new Program[0]);
            final Schedule schedule = createSchedule(copy, alignments);

            return schedule;
        }

        Schedule minSchedule = null;

        for (final Program program : programmes) {
            if (scheduledProgrammes.contains(program)) continue;

            scheduledProgrammes.add(program);
            final Schedule schedule = permutations(programmes, alignments, scheduledProgrammes);
            scheduledProgrammes.remove(program);

            minSchedule = min(minSchedule, schedule);
        }

        return minSchedule;
    }

    private Schedule min(final Schedule first, final Schedule second) {
        return Stream.of(first, second).min(Schedule.ORDER).get();
    }

    private Schedule createSchedule(
        final Program[] programmes,
        final Alignment[] alignments
    ) {
        final AlignmentError error = createAlignmentError(programmes, alignments);
        final Schedule schedule = new Schedule(programmes, error);

        return schedule;
    }

    private AlignmentError createAlignmentError(
        final Program[] programmes,
        final Alignment[] alignments
    ) {
        final TreeSet<Integer> timeIndex = createTimeIndex(programmes);

        final AlignmentError error = new AlignmentError();

        for (final Alignment alignment : alignments) {
            final int previousTime = Optional.ofNullable(timeIndex.floor(alignment.time)).orElse(0);
            final int nextTime = Optional.ofNullable(timeIndex.ceiling(alignment.time)).orElse(0);

            final int importance = alignment.importance;
            final int miss = Math.min(Math.abs(alignment.time - previousTime), Math.abs(nextTime - alignment.time));

            error.add(importance, miss);
        }

        return error;
    }

    private TreeSet<Integer> createTimeIndex(final Program[] programmes) {
        final TreeSet<Integer> timeIndex = new TreeSet<>();

        int totalTimes = 0;
        for (final Program program : programmes) {
            final int startedAt = totalTimes;
            final int finishedAt = startedAt + program.length;

            timeIndex.add(startedAt);
            timeIndex.add(finishedAt);

            totalTimes = finishedAt;
        }

        return timeIndex;
    }
}

class Program {
    public final int id;
    public final int length;

    public Program(
        final int id,
        final int length
    ) {
        this.id = id;
        this.length = length;
    }
}

class Alignment {
    public final int id;
    public final int importance;
    public final int time;

    public Alignment(
        final int id,
        final int importance,
        final int time
    ) {
        this.id = id;
        this.importance = importance;
        this.time = time;
    }
}

class Schedule {
    public static final Comparator<Schedule> ORDER = orderByErrorAndProgramLength();
    public final Program[] programmes;
    public final AlignmentError error;

    public Schedule(
        final Program[] programmes,
        final AlignmentError error
    ) {
        this.programmes = programmes;
        this.error = error;
    }

    private static Comparator<Schedule> orderByErrorAndProgramLength() {
        Comparator<Schedule> order = Comparator.comparing((Schedule s) -> s.error, AlignmentError.ORDER);

        for (int i = 0; i < Constraint.TOTAL_ALIGNMENTS_MAX; i++) {
            final int index = i;
            order = order.thenComparing((Schedule s) ->
                index < s.programmes.length ? s.programmes[index].length : 0
            );
        }

        return Comparator.nullsLast(order);
    }
}

class AlignmentError {
    public static final Comparator<AlignmentError> ORDER = orderByImportanceAndTotalMisses();
    public final int[] totalMissesPerImportance = new int[Constraint.IMPORTANCE_MAX + 1];

    public void add(
        final int importance,
        final int miss
    ) {
        totalMissesPerImportance[importance] += miss;
    }

    public int totalMisses() {
        return Arrays.stream(totalMissesPerImportance).sum();
    }

    private static Comparator<AlignmentError> orderByImportanceAndTotalMisses() {
        Comparator<AlignmentError> order = Comparator.comparingInt(e -> e.totalMissesPerImportance[1]);
        for (int i = 2; i <= Constraint.IMPORTANCE_MAX; i++) {
            final int importance = i;
            order = order.thenComparingInt(e -> e.totalMissesPerImportance[importance]);
        }
        return Comparator.nullsLast(order);
    }
}
