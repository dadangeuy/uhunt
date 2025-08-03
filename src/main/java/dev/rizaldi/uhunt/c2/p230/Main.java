package dev.rizaldi.uhunt.c2.p230;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.TreeSet;

/**
 * 230 - Borrowers
 * Time limit: 3.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&page=show_problem&category=0&problem=166&mosmsg=Submission+received+with+ID+30570599
 */
public class Main {
    public static void main(final String... args) throws IOException {
        final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

        final TreeSet<Book> shelvedBooks = new TreeSet<>();
        for (String line = in.readLine(); !line.equals("END"); line = in.readLine()) {
            final String title = line.substring(1, line.indexOf("\" by "));
            final String author = line.substring(line.indexOf("\" by ") + 5);
            final Book book = new Book(title, author);
            shelvedBooks.add(book);
        }

        final TreeSet<Book> borrowedBooks = new TreeSet<>();
        final TreeSet<Book> returnedBooks = new TreeSet<>();
        for (String line = in.readLine(); !line.equals("END"); line = in.readLine()) {
            if (line.startsWith("BORROW")) {
                final String borrowedTitle = line.substring(8, line.length() - 1);
                final Book borrowedBook = shelvedBooks.stream()
                        .filter(book -> book.title.equals(borrowedTitle))
                        .findFirst()
                        .get();

                shelvedBooks.remove(borrowedBook);
                borrowedBooks.add(borrowedBook);
            } else if (line.startsWith("RETURN")) {
                final String returnedTitle = line.substring(8, line.length() - 1);
                final Book returnedBook = borrowedBooks.stream()
                        .filter(book -> book.title.equals(returnedTitle))
                        .findFirst()
                        .get();

                borrowedBooks.remove(returnedBook);
                returnedBooks.add(returnedBook);
            } else if (line.startsWith("SHELVE")) {
                while (!returnedBooks.isEmpty()) {
                    final Book returnedBook = returnedBooks.pollFirst();
                    final Book previousBook = shelvedBooks.lower(returnedBook);
                    shelvedBooks.add(returnedBook);

                    if (previousBook == null) {
                        out.write(String.format("Put \"%s\" first\n", returnedBook.title));
                    } else {
                        out.write(String.format("Put \"%s\" after \"%s\"\n", returnedBook.title, previousBook.title));
                    }
                }
                out.write("END\n");
            }
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Book implements Comparable<Book> {
    public final String title;
    public final String author;

    public Book(final String title, final String author) {
        this.title = title;
        this.author = author;
    }

    @Override
    public int compareTo(final Book book) {
        final int sortByAuthor = author.compareTo(book.author);
        final int sortByTitle = title.compareTo(book.title);
        return sortByAuthor != 0 ? sortByAuthor : sortByTitle;
    }
}
