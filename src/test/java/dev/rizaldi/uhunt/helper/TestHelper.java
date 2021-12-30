package dev.rizaldi.uhunt.helper;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Assertions;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Objects;

public class TestHelper {
    public static void run(ThrowingRunnable main, File directory, String... files) throws Exception {
        for (String file : files) {
            File input = new File(directory, file + ".i");
            File output = new File(directory, file + ".o");
            File result = new File(directory, file + ".r");

            InputStream inputStream = new FileInputStream(input);
            PrintStream resultStream = new PrintStream(result);

            System.setIn(inputStream);
            System.setOut(resultStream);

            main.run();

            boolean match = FileUtils.contentEqualsIgnoreEOL(output, result, null);
            Assertions.assertTrue(match);
        }
    }

    public static File getDirectory(Class<?> klass) {
        String packageName = klass.getPackage().getName();
        String path = packageName
            .replace("dev.rizaldi.uhunt", "")
            .replaceAll("\\.", "/");
        try {
            URL testURL = Objects.requireNonNull(klass.getResource(path));
            URI testURI = testURL.toURI();
            return new File(testURI);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
