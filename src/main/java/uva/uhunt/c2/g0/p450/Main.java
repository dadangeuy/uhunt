package uva.uhunt.c2.g0.p450;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * 450 - Little Black Book
 * Time limit: 3.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=391
 */
public class Main {
    private static final String DELIMITER = ",";

    public static void main(final String... args) {
        final Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 16));
        final PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 16));

        final List<Contact> contacts = new ArrayList<>();
        final int totalDepartments = Integer.parseInt(in.nextLine());

        for (int i = 0; i < totalDepartments; i++) {
            final String department = in.nextLine();

            while (in.hasNextLine()) {
                final String nextLine = in.nextLine();
                final String[] data = nextLine.split(DELIMITER);
                if (data.length != 7) break;

                final Contact contact = new Contact(
                        data[0],
                        data[1],
                        data[2],
                        data[3],
                        department,
                        data[4],
                        data[5],
                        data[6]
                );
                contacts.add(contact);
            }
        }

        contacts.sort(Contact::compareTo);

        contacts.forEach(contact -> out.print(contact.getSummary()));

        in.close();
        out.flush();
        out.close();
    }
}

class Contact implements Comparable<Contact> {
    private static final String SEPARATOR = "----------------------------------------";
    private final String title;
    private final String firstName;
    private final String lastName;
    private final String homeAddress;
    private final String department;
    private final String homePhone;
    private final String workPhone;
    private final String campusBox;

    public Contact(
            final String title,
            final String firstName,
            final String lastName,
            final String homeAddress,
            final String department,
            final String homePhone,
            final String workPhone,
            final String campusBox
    ) {
        this.title = title;
        this.firstName = firstName;
        this.lastName = lastName;
        this.homeAddress = homeAddress;
        this.department = department;
        this.homePhone = homePhone;
        this.workPhone = workPhone;
        this.campusBox = campusBox;
    }

    public String getSummary() {
        return String.format(
                "%s\n%s %s %s\n%s\nDepartment: %s\nHome Phone: %s\nWork Phone: %s\nCampus Box: %s\n",
                SEPARATOR,
                title,
                firstName,
                lastName,
                homeAddress,
                department,
                homePhone,
                workPhone,
                campusBox
        );
    }

    @Override
    public int compareTo(Contact o) {
        return lastName.compareTo(o.lastName);
    }
}
