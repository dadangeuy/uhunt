package dev.rizaldi.uhunt.c2.p394;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * 394 - Mapmaker
 * Time limit: 3.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=330
 */
public class Main {
    private static final int BUFFER_SIZE = 1 << 16;

    public static void main(String... args) throws IOException {
        final BufferedReader in = new BufferedReader(new InputStreamReader(System.in), BUFFER_SIZE);
        final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out), BUFFER_SIZE);

        final String[] totalArraysAndReferences = in.readLine().trim().split("\\s+");
        int totalArrays = Integer.parseInt(totalArraysAndReferences[0]);
        int totalReferences = Integer.parseInt(totalArraysAndReferences[1]);

        final Array[] arrays = new Array[totalArrays];
        final Map<String, Array> arrayPerName = new HashMap<>();
        for (int i = 0; i < totalArrays; i++) {
            final Array array = new Array();
            arrays[i] = array;

            final String[] line = in.readLine().trim().split("\\s+");

            array.name = line[0];
            array.baseAddress = Integer.parseInt(line[1]);
            array.size = Integer.parseInt(line[2]);
            array.dimension = Integer.parseInt(line[3]);
            array.lowerBounds = new int[array.dimension];
            array.upperBounds = new int[array.dimension];
            for (int j = 0; j < array.dimension; j++) {
                array.lowerBounds[j] = Integer.parseInt(line[4 + j * 2]);
                array.upperBounds[j] = Integer.parseInt(line[4 + j * 2 + 1]);
            }

            arrayPerName.put(array.name, array);
        }

        final Reference[] references = new Reference[totalReferences];
        for (int i = 0; i < totalReferences; i++) {
            final Reference reference = new Reference();
            references[i] = reference;

            final String[] line = in.readLine().trim().split("\\s+");

            reference.name = line[0];
            reference.indexes = new int[line.length - 1];
            for (int j = 0; j < reference.indexes.length; j++) {
                reference.indexes[j] = Integer.parseInt(line[j + 1]);
            }
        }

        for (final Reference reference : references) {
            out
                    .append(reference.name)
                    .append('[');
            for (int i = 0; i < reference.indexes.length - 1; i++) {
                out
                        .append(Integer.toString(reference.indexes[i]))
                        .append(", ");
            }
            out
                    .append(Integer.toString(reference.indexes[reference.indexes.length - 1]))
                    .append("] = ")
                    .append(Integer.toString(arrayPerName.get(reference.name).getPhysicalAddress(reference.indexes)))
                    .append("\n");
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Array {
    public String name;
    public int baseAddress;
    public int size;
    public int dimension;
    public int[] lowerBounds;
    public int[] upperBounds;

    public int getPhysicalAddress(int[] indexes) {
        return getPhysicalAddress(lowerBounds.clone(), indexes);
    }

    private int getPhysicalAddress(int[] currentIndexes, int[] targetIndexes) {
        int physicalAddress = baseAddress;
        while (!equal(currentIndexes, targetIndexes)) {
            increment(currentIndexes);
            physicalAddress += size;
        }
        return physicalAddress;
    }

    private boolean equal(int[] source, int[] target) {
        for (int i = 0; i < source.length; i++) {
            if (source[i] != target[i]) {
                return false;
            }
        }
        return true;
    }

    private void increment(int[] index) {
        for (int i = index.length - 1; i >= 0; i--) {
            index[i]++;
            if (index[i] <= upperBounds[i]) {
                break;
            } else {
                index[i] = lowerBounds[i];
            }
        }
    }
}

class Reference {
    public String name;
    public int[] indexes;
}
