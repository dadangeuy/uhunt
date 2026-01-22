package uhunt.c1.g6.p1586;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String... args) {
        Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 16));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 16));

        int totalCase = in.nextInt();
        for (int i = 0; i < totalCase; i++) {
            String compound = in.next();

            Solution solution = new Solution(compound);
            int mass = solution.getMass();

            out.format("%d.%03d\n", mass / 1_000, mass % 1_000);
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Solution {
    private static final int CARBON = 12_010;
    private static final int HYDROGEN = 1_008;
    private static final int OXYGEN = 16_000;
    private static final int NITROGEN = 14_010;
    private static final Pattern moleculePattern = Pattern.compile("[A-Z][0-9]*");
    private final String compound;

    public Solution(String compound) {
        this.compound = compound;
    }

    private static int getWeight(char atom) {
        switch (atom) {
            case 'C':
                return CARBON;
            case 'H':
                return HYDROGEN;
            case 'O':
                return OXYGEN;
            case 'N':
                return NITROGEN;
        }
        return 0;
    }

    public int getMass() {
        int compoundMass = 0;

        Matcher matcher = moleculePattern.matcher(compound);
        while (matcher.find()) {
            String molecule = matcher.group();

            char atom = molecule.charAt(0);
            int count = molecule.length() == 1 ? 1 : Integer.parseInt(molecule.substring(1));

            int atomWeight = getWeight(atom);
            int moleculeMass = atomWeight * count;
            compoundMass += moleculeMass;
        }

        return compoundMass;
    }
}
