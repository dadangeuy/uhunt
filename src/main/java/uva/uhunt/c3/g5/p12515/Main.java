package uva.uhunt.c3.g5.p12515;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * 12515 - Movie Police
 * Time limit: 4.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=3960
 */
public class Main {
    public static void main(final String... args) {
        final Scanner in = new Scanner(new BufferedInputStream(System.in));
        final PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));
        final Process process = new Process();

        final Input input = new Input();
        input.totalMovies = in.nextInt();
        input.totalClips = in.nextInt();
        input.movies = new String[input.totalMovies];
        input.clips = new String[input.totalClips];
        for (int i = 0; i < input.totalMovies; i++) {
            input.movies[i] = in.next();
        }
        for (int i = 0; i < input.totalClips; i++) {
            input.clips[i] = in.next();
        }

        final Output output = process.process(input);
        for (final int index : output.indexes) {
            out.println(index);
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Input {
    public int totalMovies;
    public int totalClips;
    public String[] movies;
    public String[] clips;
}

class Output {
    public int[] indexes;
}

class Process {
    public Output process(final Input input) {
        final Output output = new Output();
        output.indexes = new int[input.totalClips];

        for (int i = 0; i < input.totalClips; i++) {
            final String clip = input.clips[i];
            int minimumSimilarity = Integer.MAX_VALUE;
            int minimumIndex = 0;

            for (int j = 0; j < input.totalMovies; j++) {
                final String movie = input.movies[j];
                final int similarity = getSimilarity(clip, movie);

                if (similarity < minimumSimilarity) {
                    minimumSimilarity = similarity;
                    minimumIndex = j + 1;
                }
            }

            output.indexes[i] = minimumIndex;
        }

        return output;
    }

    private int getSimilarity(final String clip, final String movie) {
        int minimumDistance = Integer.MAX_VALUE;
        for (int i = 0; i + clip.length() <= movie.length(); i++) {
            final String movieClip = movie.substring(i, i + clip.length());
            final int distance = getHammingDistance(clip, movieClip);
            minimumDistance = Math.min(minimumDistance, distance);
        }

        return minimumDistance;
    }

    private int getHammingDistance(final String signature1, final String signature2) {
        int distance = 0;
        for (int i = 0; i < signature1.length(); i++) {
            if (signature1.charAt(i) != signature2.charAt(i)) {
                distance++;
            }
        }

        return distance;
    }
}
