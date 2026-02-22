package uhunt.c3.g2.p592;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 592 - Island of Logic
 * Time limit: 3.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=533
 */
public class Main {
    public static void main(final String... args) throws IOException {
        final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));
        final Process process = new Process();

        for (int id = 1; ; id++) {
            final Input input = new Input();
            input.id = id;
            input.totalStatements = Integer.parseInt(readLine(in));
            if (input.isEOF()) break;
            input.statements = new String[input.totalStatements];
            for (int i = 0; i < input.totalStatements; i++) input.statements[i] = readLine(in);

            final Output output = process.process(input);
            out.write("Conversation #");
            out.write(Integer.toString(output.id));
            out.write('\n');

            if (output.isImpossible) {
                out.write("This is impossible.\n");
            } else if (output.isNotDeducible) {
                out.write("No facts are deducible.\n");
            } else {
                for (final String fact : output.facts) {
                    out.write(fact);
                    out.write('\n');
                }
            }

            out.write('\n');
        }

        in.close();
        out.flush();
        out.close();
    }

    private static String readLine(final BufferedReader in) throws IOException {
        final String line = in.readLine();
        return line == null || !line.isEmpty() ? line : readLine(in);
    }
}

class Input {
    public int id;
    public int totalStatements;
    public String[] statements;

    public boolean isEOF() {
        return totalStatements == 0;
    }
}

class Output {
    public final int id;
    public final boolean isImpossible;
    public final boolean isNotDeducible;
    public final List<String> facts;

    public Output(final int id, final boolean isImpossible, final boolean isNotDeducible, final List<String> facts) {
        this.id = id;
        this.isImpossible = isImpossible;
        this.isNotDeducible = isNotDeducible;
        this.facts = facts;
    }
}

class Process {
    private static final List<Condition> CONDITIONS = permutations();

    private static final Set<String> DIVINE_PREDICATES = new HashSet<>(Arrays.asList(
        "am divine", "am not human", "am not evil", "am not lying",
        "is divine", "is not human", "is not evil", "is not lying"
    ));
    private static final Set<String> EVIL_PREDICATES = new HashSet<>(Arrays.asList(
        "am evil", "am lying", "am not divine", "am not human",
        "is evil", "is lying", "is not divine", "is not human"
    ));
    private static final Set<String> DAY_HUMAN_PREDICATES = new HashSet<>(Arrays.asList(
        "am human", "am not divine", "am not evil", "am not lying",
        "is human", "is not divine", "is not evil", "is not lying"
    ));
    private static final Set<String> NIGHT_HUMAN_PREDICATES = new HashSet<>(Arrays.asList(
        "am human", "am lying", "am not divine", "am not evil",
        "is human", "is lying", "is not divine", "is not evil"
    ));
    private static final Set<String> DAY_PREDICATES = new HashSet<>(Arrays.asList(
        "is day", "is not night"
    ));
    private static final Set<String> NIGHT_PREDICATES = new HashSet<>(Arrays.asList(
        "is night", "is not day"
    ));

    public Output process(final Input input) {
        final List<Statement> statements = Arrays.stream(input.statements)
            .map(Statement::new)
            .collect(Collectors.toList());

        final List<Condition> conditions = CONDITIONS.stream()
            .filter(condition -> validate(statements, condition))
            .collect(Collectors.toList());

        if (conditions.isEmpty()) {
            return new Output(input.id, true, false, null);
        }

        final Set<String> timeOptions = new HashSet<>();
        final Map<String, Set<String>> beingOptions = new HashMap<>();
        for (final Condition condition : conditions) {
            timeOptions.add(condition.time);
            for (int i = 0; i < condition.names.length; i++) {
                final String name = condition.names[i];
                final String being = condition.beings[i];
                beingOptions.computeIfAbsent(name, k -> new HashSet<>()).add(being);
            }
        }

        final List<String> facts = new LinkedList<>();
        for (final Map.Entry<String, Set<String>> entry : beingOptions.entrySet()) {
            final String name = entry.getKey();
            final Set<String> beings = entry.getValue();
            if (beings.size() == 1) facts.add(String.format("%s is %s.", name, beings.iterator().next()));
        }
        if (timeOptions.size() == 1) {
            facts.add(String.format("It is %s.", timeOptions.iterator().next()));
        }

        return new Output(input.id, false, facts.isEmpty(), facts);
    }

    private boolean validate(final List<Statement> statements, final Condition condition) {
        final List<Statement> directFacts = statements.stream()
            .filter(statement -> condition.canTrust(statement.speaker()))
            .collect(Collectors.toList());
        final List<Statement> indirectFacts = statements.stream()
            .filter(statement -> condition.canNotTrust(statement.speaker()))
            .map(Statement::reverse)
            .collect(Collectors.toList());
        final List<Statement> facts = Stream
            .concat(directFacts.stream(), indirectFacts.stream())
            .collect(Collectors.toList());

        final List<String> timePredicates = facts.stream()
            .filter(fact -> fact.noun().equals("It"))
            .map(Statement::predicate)
            .collect(Collectors.toList());
        switch (condition.time) {
            case "day": {
                final boolean valid = DAY_PREDICATES.containsAll(timePredicates);
                if (!valid) return false;
                break;
            }
            case "night": {
                final boolean valid = NIGHT_PREDICATES.containsAll(timePredicates);
                if (!valid) return false;
                break;
            }
        }

        final String time = condition.time;
        for (int i = 0; i < condition.names.length; i++) {
            final String name = condition.names[i];
            final String being = condition.beings[i];

            final List<Statement> internalFacts = facts.stream()
                .filter(fact -> fact.noun().equals("I") && fact.speaker().equals(name))
                .collect(Collectors.toList());
            final List<Statement> externalFacts = facts.stream()
                .filter(fact -> fact.noun().equals(name))
                .collect(Collectors.toList());
            final List<Statement> myFacts = Stream
                .concat(internalFacts.stream(), externalFacts.stream())
                .collect(Collectors.toList());

            final List<String> predicates = myFacts.stream()
                .map(Statement::predicate)
                .collect(Collectors.toList());

            switch (being) {
                case "divine": {
                    final boolean valid = DIVINE_PREDICATES.containsAll(predicates);
                    if (!valid) return false;
                    break;
                }
                case "evil": {
                    final boolean valid = EVIL_PREDICATES.containsAll(predicates);
                    if (!valid) return false;
                    break;
                }
                case "human": {
                    switch (time) {
                        case "day": {
                            final boolean valid = DAY_HUMAN_PREDICATES.containsAll(predicates);
                            if (!valid) return false;
                            break;
                        }
                        case "night": {
                            final boolean valid = NIGHT_HUMAN_PREDICATES.containsAll(predicates);
                            if (!valid) return false;
                            break;
                        }
                    }
                    break;
                }
            }
        }

        return true;
    }

    private static LinkedList<Condition> permutations() {
        final String[] times = new String[]{"day", "night"};
        final String[] beings = new String[]{"divine", "evil", "human"};
        final String[] names = new String[]{"A", "B", "C", "D", "E"};
        return permutation(times, beings, names);
    }

    private static LinkedList<Condition> permutation(
        final String[] times,
        final String[] beings,
        final String[] names
    ) {
        final LinkedList<Condition> conditions = new LinkedList<>();

        final Condition.Builder builder = new Condition.Builder();
        builder.beings = new String[names.length];
        builder.names = names;

        for (final String time : times) {
            builder.time = time;
            for (final String being0 : beings) {
                builder.beings[0] = being0;
                for (final String being1 : beings) {
                    builder.beings[1] = being1;
                    for (final String being2 : beings) {
                        builder.beings[2] = being2;
                        for (final String being3 : beings) {
                            builder.beings[3] = being3;
                            for (final String being4 : beings) {
                                builder.beings[4] = being4;
                                conditions.add(builder.build());
                            }
                        }
                    }
                }
            }
        }

        return conditions;
    }
}

class Condition {
    public final String time;
    public final String[] beings;
    public final String[] names;

    private Condition(Builder builder) {
        this.time = builder.time;
        this.beings = Arrays.copyOf(builder.beings, builder.beings.length);
        this.names = Arrays.copyOf(builder.names, builder.names.length);
    }

    public boolean canTrust(final String name) {
        final int idx = name.charAt(0) - 'A';
        final boolean trust1 = "divine".equals(beings[idx]);
        final boolean trust2 = "human".equals(beings[idx]) && "day".equals(time);
        return trust1 || trust2;
    }

    public boolean canNotTrust(final String name) {
        final int idx = name.charAt(0) - 'A';
        final boolean trust1 = "evil".equals(beings[idx]);
        final boolean trust2 = "human".equals(beings[idx]) && "night".equals(time);
        return trust1 || trust2;
    }

    public static class Builder {
        public String time;
        public String[] beings;
        public String[] names;

        public Condition build() {
            return new Condition(this);
        }
    }
}

class Statement {
    public final Pattern patternIAM = Pattern.compile("([A-E]): (I) (am not|am) (divine|human|evil|lying).");
    public final Pattern patternXIS = Pattern.compile("([A-E]): ([A-E]) (is not|is) (divine|human|evil|lying).");
    public final Pattern patternITIS = Pattern.compile("([A-E]): (It) (is not|is) (day|night).");

    public final String statement;
    public final Matcher matcher;

    public Statement(final String statement) {
        this.statement = statement;
        this.matcher = Stream.of(patternIAM, patternXIS, patternITIS)
            .map(pattern -> pattern.matcher(statement))
            .filter(Matcher::matches)
            .findFirst()
            .orElseThrow(NullPointerException::new);
    }

    public String speaker() {
        return matcher.group(1);
    }

    public String noun() {
        return matcher.group(2);
    }

    public String copula() {
        return matcher.group(3);
    }

    public String adjective() {
        return matcher.group(4);
    }

    public String predicate() {
        return copula() + " " + adjective();
    }

    public Statement reverse() {
        final String copula = copula();
        if (copula.equals("am")) return new Statement(statement.replace("am", "am not"));
        else if (copula.equals("am not")) return new Statement(statement.replace("am not", "am"));
        else if (copula.equals("is")) return new Statement(statement.replace("is", "is not"));
        else if (copula.equals("is not")) return new Statement(statement.replace("is not", "is"));
        throw new NullPointerException();
    }
}
