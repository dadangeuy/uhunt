package dev.rizaldi.uhunt.p12541;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class Main {
    public static void main(String... args) {
        Scanner in = new Scanner(System.in);
        int totalPerson = in.nextInt();
        Person[] persons = new Person[totalPerson];
        for (int i = 0; i < totalPerson; i++) {
            String name = in.next();
            int day = in.nextInt();
            int month = in.nextInt();
            int year = in.nextInt();

            Person person = new Person(name, year, month, day);
            persons[i] = person;
        }

        Comparator<Person> orderByBirthdateAsc = Comparator
                .<Person>comparingInt(p -> p.year)
                .thenComparingInt(p -> p.month)
                .thenComparingInt(p -> p.day);
        Arrays.sort(persons, orderByBirthdateAsc);

        System.out.println(persons[persons.length - 1].name);
        System.out.println(persons[0].name);
    }
}

class Person {
    String name;
    int year;
    int month;
    int day;

    public Person(String name, int year, int month, int day) {
        this.name = name;
        this.year = year;
        this.month = month;
        this.day = day;
    }
}
