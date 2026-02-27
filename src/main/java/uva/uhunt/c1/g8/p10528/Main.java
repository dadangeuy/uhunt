package uva.uhunt.c1.g8.p10528;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

public class Main {
    public static void main(String... args) {
        Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 16));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 16));

        while (true) {
            String[] notes = in.nextLine().split(" ");
            if (notes[0].equals("END")) break;

            Solution solution = new Solution(notes);
            String[] keys = solution.getKeys();

            for (int i = 0; i < keys.length; i++) {
                if (i > 0) out.print(' ');
                out.print(keys[i]);
            }
            out.println();
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Solution {
    private static final String[] chromaticScale = new String[]{"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};
    private static final String[] majorNotes = new String[]{"tone", "tone", "semi-tone", "tone", "tone", "tone", "semi-tone"};
    private final String[] notes;

    public Solution(String[] notes) {
        this.notes = notes;
    }

    private static boolean isComposed(String[] notes, Set<String> scale) {
        boolean composed = true;
        for (String note : notes) {
            if (!scale.contains(note)) {
                return false;
            }
        }
        return composed;
    }

    private static Set<String> asSet(String[] values) {
        return Arrays.stream(values).collect(Collectors.toSet());
    }

    private static String[] getMajorScale(String key) {
        int id = 0;
        while (!chromaticScale[id].equals(key)) id++;

        String[] scale = new String[8];
        scale[0] = key;
        for (int i = 1; i < scale.length; i++) {
            String note = majorNotes[i - 1];
            if (note.equals("tone")) {
                id = id + 2;
                scale[i] = chromaticScale[id % chromaticScale.length];
            } else if (note.equals("semi-tone")) {
                id = id + 1;
                scale[i] = chromaticScale[id % chromaticScale.length];
            }
        }

        return scale;
    }

    public String[] getKeys() {
        LinkedList<String> keys = new LinkedList<>();

        for (String key : chromaticScale) {
            Set<String> scale = asSet(getMajorScale(key));
            if (isComposed(notes, scale)) keys.addLast(key);
        }

        return keys.toArray(new String[0]);
    }
}
