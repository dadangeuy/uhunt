package uhunt.c2.p939;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 939 - Genes
 * Time limit: 3.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=880
 */
public class Main {
    public static void main(String... args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

        Input input = new Input();
        int totalLines = Integer.parseInt(in.readLine());
        for (int i = 0; i < totalLines; i++) {
            String[] line = in.readLine().split(" ");
            if (Status.isValid(line[1])) {
                input.listPersonAndStatus.addLast(line);
            } else {
                input.listParentAndChild.addLast(line);
            }
        }

        Output output = new Output();
        Process process = new Process(input, output);
        process.process();
        output.print(out);

        in.close();
        out.flush();
        out.close();
    }
}

/**
 * algorithm:
 * - store relationship using hashmap (child -> parent)
 * - store status using hashmap (person -> status)
 * - use recursive dfs to explore all relationship
 * - dominant if: both parent dominant, or dominant-recesive
 * - recessive if: not dominant and either parent have gene
 * - non-existent if: not dominant and not recessive
 */
class Process {
    private final Input input;
    private final Output output;
    private Map<String, Set<String>> parentsForEachChild;
    private Map<String, String> statusForEachPerson;
    private Set<String> persons;
    private Set<String> traversedPerson;

    public Process(Input input, Output output) {
        this.input = input;
        this.output = output;
    }

    public void process() {
        buildRelationship();
        buildStatus();
        buildPersons();
        traverseRelationship();
        fillEachPersonStatusAndSortByPerson();
    }

    private void buildRelationship() {
        parentsForEachChild = new HashMap<>();
        for (String[] parentAndChild : input.listParentAndChild) {
            String parent = parentAndChild[0];
            String child = parentAndChild[1];
            parentsForEachChild.computeIfAbsent(child, k -> new HashSet<>()).add(parent);
        }
    }

    private void buildStatus() {
        statusForEachPerson = new HashMap<>();
        for (String[] personAndStatus : input.listPersonAndStatus) {
            String person = personAndStatus[0];
            String status = personAndStatus[1];
            statusForEachPerson.put(person, status);
        }
    }

    private void buildPersons() {
        persons = new HashSet<>();
        for (Entry<String, Set<String>> entry : parentsForEachChild.entrySet()) {
            persons.add(entry.getKey());
            persons.addAll(entry.getValue());
        }
        persons.addAll(statusForEachPerson.keySet());
    }

    private void traverseRelationship() {
        traversedPerson = new HashSet<>();
        for (String person : persons) {
            traverseParent(person);
        }
    }

    private void traverseParent(String person) {
        if (traversedPerson.contains(person)) return;
        traversedPerson.add(person);

        Set<String> parents = parentsForEachChild.getOrDefault(person, Collections.emptySet());
        for (String parent : parents) {
            traverseParent(parent);
        }

        calculateStatus(person, parents);
    }

    private void calculateStatus(String person, Set<String> parents) {
        if (statusForEachPerson.containsKey(person)) return;

        List<String> parentStatuses = parents.stream()
                .map(parent -> statusForEachPerson.getOrDefault(parent, Status.NON_EXISTENT))
                .collect(Collectors.toList());
        long countDominant = parentStatuses.stream()
                .filter(status -> status.equals(Status.DOMINANT))
                .count();
        long countRecessive = parentStatuses.stream()
                .filter(status -> status.equals(Status.RECESSIVE))
                .count();

        boolean bothDominant = countDominant == 2;
        boolean dominantAndRecessive = countDominant == 1 && countRecessive == 1;
        boolean bothRecessive = countRecessive == 2;
        boolean eitherDominant = countDominant == 1;

        if (bothDominant || dominantAndRecessive) {
            statusForEachPerson.put(person, Status.DOMINANT);
        } else if (bothRecessive || eitherDominant) {
            statusForEachPerson.put(person, Status.RECESSIVE);
        } else {
            statusForEachPerson.put(person, Status.NON_EXISTENT);
        }
    }

    private void fillEachPersonStatusAndSortByPerson() {
        List<String> sortedPersons = persons.stream().sorted().collect(Collectors.toList());
        for (String person : sortedPersons) {
            String status = statusForEachPerson.getOrDefault(person, Status.NON_EXISTENT);
            output.listPersonAndStatus.addLast(new String[]{person, status});
        }
    }
}

class Status {
    public static final String NON_EXISTENT = "non-existent";
    public static final String RECESSIVE = "recessive";
    public static final String DOMINANT = "dominant";

    public static boolean isValid(String status) {
        return status.equals(NON_EXISTENT) || status.equals(RECESSIVE) || status.equals(DOMINANT);
    }
}

class Input {
    public final LinkedList<String[]> listPersonAndStatus = new LinkedList<>();
    public final LinkedList<String[]> listParentAndChild = new LinkedList<>();
}

class Output {
    public final LinkedList<String[]> listPersonAndStatus = new LinkedList<>();

    public void print(BufferedWriter out) throws IOException {
        for (String[] personAndStatus : listPersonAndStatus) {
            out.append(personAndStatus[0]).append(' ').append(personAndStatus[1]).append('\n');
        }
    }
}
