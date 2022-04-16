package dev.rizaldi.uhunt.c1.p1061;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.*;

public class Main {
    public static void main(String... args) {
        Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 16));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 16));

        for (int caseNum = 1; ; caseNum++) {
            String parent1 = in.next();
            String parent2 = in.next();
            String child = in.next();
            if ((parent1 + parent2 + child).equals("END")) break;

            Solution solution = new Solution(parent1, parent2, child);
            if (parent1.equals("?")) {
                String[] parent1s = solution.getParent1BloodTypes();
                parent1 = asBloodTypesString(parent1s);

            } else if (parent2.equals("?")) {
                String[] parent2s = solution.getParent2BloodTypes();
                parent2 = asBloodTypesString(parent2s);

            } else if (child.equals("?")) {
                String[] childs = solution.getChildBloodTypes();
                child = asBloodTypesString(childs);

            }

            out.format("Case %d: %s %s %s\n", caseNum, parent1, parent2, child);
        }

        in.close();
        out.flush();
        out.close();
    }

    private static String asBloodTypesString(String[] bloodTypes) {
        return bloodTypes.length == 0 ? "IMPOSSIBLE"
            : bloodTypes.length == 1 ? bloodTypes[0]
            : "{" + String.join(", ", bloodTypes) + "}";
    }
}

class Solution {
    private static final String[] ABO_BLOOD_TYPES = new String[]{"A", "AB", "B", "O"};
    private static final String[] RHS = new String[]{"+", "-"};
    private static final String[] BLOOD_MARKERS = new String[]{"AA", "AB", "AO", "BB", "BO", "OO"};
    private static final Map<String, String[]> BLOOD_MARKERS_PER_ABO_BLOOD_TYPE = new HashMap<String, String[]>() {{
        put("A", new String[]{"AA", "AO"});
        put("AB", new String[]{"AB"});
        put("B", new String[]{"BB", "BO"});
        put("O", new String[]{"OO"});
    }};
    private static final Map<String, String> ABO_BLOOD_TYPE_PER_BLOOD_MARKER = new HashMap<String, String>() {{
        put("AA", "A");
        put("AB", "AB");
        put("AO", "A");
        put("BB", "B");
        put("BO", "B");
        put("OO", "O");
    }};
    private static final Map<String, String[]> ALLELE_RHS_PER_RH = new HashMap<String, String[]>() {{
        put("+", new String[]{"+", "-"});
        put("-", new String[]{"-"});
    }};

    private final String p1BloodType;
    private final String p2BloodType;
    private final String cBloodType;

    public Solution(String parent1, String parent2, String child) {
        this.p1BloodType = parent1;
        this.p2BloodType = parent2;
        this.cBloodType = child;
    }

    public String[] getParent1BloodTypes() {
        return getMissingParentBloodTypes(p2BloodType, cBloodType);
    }

    public String[] getParent2BloodTypes() {
        return getMissingParentBloodTypes(p1BloodType, cBloodType);
    }

    private String[] getMissingParentBloodTypes(String p1BloodType, String cBloodType) {
        String p1AboBloodType = p1BloodType.substring(0, p1BloodType.length() - 1);
        String p1Rh = p1BloodType.substring(p1BloodType.length() - 1);
        String[] p1BloodMarkers = BLOOD_MARKERS_PER_ABO_BLOOD_TYPE.get(p1AboBloodType);
        String[] p1AlleleRhs = ALLELE_RHS_PER_RH.get(p1Rh);

        Set<String> p2BloodTypes = new TreeSet<>();

        for (String p1BloodMarker : p1BloodMarkers) {
            for (String p1Allele : p1BloodMarker.split("")) {
                for (String p1AlleleRh : p1AlleleRhs) {

                    for (String p2AboBloodType : ABO_BLOOD_TYPES) {
                        for (String p2Rh : RHS) {

                            for (String p2BloodMarker : BLOOD_MARKERS_PER_ABO_BLOOD_TYPE.get(p2AboBloodType)) {
                                for (String p2Allele : p2BloodMarker.split("")) {
                                    for (String p2AlleleRh : ALLELE_RHS_PER_RH.get(p2Rh)) {

                                        String bloodType = buildChildBloodType(p1Allele, p1AlleleRh, p2Allele, p2AlleleRh);
                                        if (!bloodType.equals(cBloodType)) continue;
                                        p2BloodTypes.add(p2AboBloodType + p2Rh);

                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return p2BloodTypes.toArray(new String[0]);
    }

    public String[] getChildBloodTypes() {
        String p1AboBloodType = getAboBloodType(p1BloodType);
        String p1Rh = getRh(p1BloodType);
        String[] p1BloodMarkers = BLOOD_MARKERS_PER_ABO_BLOOD_TYPE.get(p1AboBloodType);
        String[] p1AlleleRhs = ALLELE_RHS_PER_RH.get(p1Rh);

        String p2AboBloodType = getAboBloodType(p2BloodType);
        String p2Rh = getRh(p2BloodType);
        String[] p2BloodMarkers = BLOOD_MARKERS_PER_ABO_BLOOD_TYPE.get(p2AboBloodType);
        String[] p2AlleleRhs = ALLELE_RHS_PER_RH.get(p2Rh);

        Set<String> cBloodTypes = new TreeSet<>();

        for (String p1BloodMarker : p1BloodMarkers) {
            for (String p1Allele : p1BloodMarker.split("")) {
                for (String p1AlleleRh : p1AlleleRhs) {

                    for (String p2BloodMarker : p2BloodMarkers) {
                        for (String p2Allele : p2BloodMarker.split("")) {
                            for (String p2AlleleRh : p2AlleleRhs) {

                                String cBloodType = buildChildBloodType(p1Allele, p1AlleleRh, p2Allele, p2AlleleRh);
                                cBloodTypes.add(cBloodType);

                            }
                        }
                    }
                }
            }
        }

        return cBloodTypes.toArray(new String[0]);
    }

    private String getAboBloodType(String bloodType) {
        return bloodType.substring(0, bloodType.length() - 1);
    }

    private String getRh(String bloodType) {
        return bloodType.substring(bloodType.length() - 1);
    }

    private String buildChildBloodType(String allele1, String rh1, String allele2, String rh2) {
        String bloodMarker = buildChildBloodMarker(allele1, allele2);
        String aboBloodType = ABO_BLOOD_TYPE_PER_BLOOD_MARKER.get(bloodMarker);
        String rh = buildChildRh(rh1, rh2);
        return aboBloodType + rh;
    }

    private String buildChildBloodMarker(String allele1, String allele2) {
        String[] markers = new String[]{allele1, allele2};
        Arrays.sort(markers);
        return String.join("", markers);
    }

    private String buildChildRh(String rh1, String rh2) {
        boolean negative = rh1.equals("-") && rh2.equals("-");
        return negative ? "-" : "+";
    }
}
