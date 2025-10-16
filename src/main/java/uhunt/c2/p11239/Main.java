package uhunt.c2.p11239;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 11239 - Open Source
 * Time limit: 3.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=2180=
 */
public class Main {
    public static void main(final String... args) throws IOException {
        final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

        final LinkedList<Input> inputs = new LinkedList<>();

        while (true) {
            String line = in.readLine();
            if (line.equals("0")) break;

            final Input input = new Input();
            inputs.addLast(input);

            while (!line.equals("1")) {
                if (isCapital(line.charAt(0))) {
                    input.projects.addLast(line);
                    input.students.addLast(new LinkedList<>());
                } else {
                    input.students.getLast().addLast(line);
                }
                line = in.readLine();
            }
        }

        for (Input input : inputs) {
            Process process = new Process(input);
            Output output = process.getOutput();
            output.print(out);
        }

        in.close();
        out.flush();
        out.close();
    }

    private static boolean isCapital(char c) {
        return 'A' <= c && c <= 'Z';
    }
}

class Process {
    public final Input input;
    public final Output output;
    public Map<String, Set<String>> projectsPerStudent;
    public Map<String, Set<String>> studentsPerProject;
    public Set<String> duplicateStudents;

    public Process(final Input input) {
        this.input = input;
        this.output = new Output();
    }

    public Output getOutput() {
        createProjectAndStudentMapping();
        findDuplicateStudents();
        removeDuplicateStudents();
        retrieveAndSortProjects();

        return output;
    }

    private void createProjectAndStudentMapping() {
        projectsPerStudent = new HashMap<>();
        studentsPerProject = new HashMap<>();

        Iterator<String> it1 = input.projects.iterator();
        Iterator<LinkedList<String>> it2 = input.students.iterator();
        while (it1.hasNext() && it2.hasNext()) {
            String project = it1.next();
            LinkedList<String> students = it2.next();

            studentsPerProject.computeIfAbsent(project, k -> new HashSet<>());
            for (String student : students) {
                projectsPerStudent.computeIfAbsent(student, k -> new HashSet<>()).add(project);
                studentsPerProject.computeIfAbsent(project, k -> new HashSet<>()).add(student);
            }
        }
    }

    private void findDuplicateStudents() {
        duplicateStudents = projectsPerStudent.entrySet().stream()
                .filter(entry -> entry.getValue().size() > 1)
                .map(Entry::getKey)
                .collect(Collectors.toSet());
    }

    private void removeDuplicateStudents() {
        for (String duplicateStudent : duplicateStudents) {
            for (String project : projectsPerStudent.getOrDefault(duplicateStudent, Collections.emptySet())) {
                studentsPerProject.getOrDefault(project, Collections.emptySet()).remove(duplicateStudent);
            }
            projectsPerStudent.get(duplicateStudent).clear();
        }
    }

    private void retrieveAndSortProjects() {
        List<Entry<String, Set<String>>> entries = studentsPerProject.entrySet().stream()
                .sorted((entry1, entry2) -> {
                    int totalStudent1 = entry1.getValue().size();
                    int totalStudent2 = entry2.getValue().size();
                    int comparison1 = totalStudent2 - totalStudent1;
                    int comparison2 = entry1.getKey().compareTo(entry2.getKey());

                    return comparison1 != 0? comparison1 : comparison2;
                })
                .collect(Collectors.toList());

        for (Entry<String, Set<String>> entry : entries) {
            output.projects.addLast(entry.getKey());
            output.totalStudents.addLast(entry.getValue().size());
        }
    }
}

class Input {
    public final LinkedList<String> projects = new LinkedList<>();
    public final LinkedList<LinkedList<String>> students = new LinkedList<>();
}

class Output {
    public final LinkedList<String> projects = new LinkedList<>();
    public final LinkedList<Integer> totalStudents = new LinkedList<>();

    public void print(BufferedWriter out) throws IOException {
        Iterator<String> it1 = projects.iterator();
        Iterator<Integer> it2 = totalStudents.iterator();

        while (it1.hasNext() && it2.hasNext()) {
            out
                    .append(it1.next())
                    .append(' ')
                    .append(it2.next().toString())
                    .append('\n');
        }
    }
}
