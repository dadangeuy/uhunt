package dev.rizaldi.uhunt.helper;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.junit.jupiter.api.Assertions;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.concurrent.Callable;

public class TestFileHelper {
    public static void runTest(File testDir, Runnable mainRun) throws Exception {
        File[] inputFiles = testDir.listFiles((f) -> f.getName().endsWith(".in"));
        File[] outputFiles = testDir.listFiles((f) -> f.getName().endsWith(".out"));

        Assertions.assertNotNull(inputFiles);
        Assertions.assertNotNull(outputFiles);
        Assertions.assertEquals(inputFiles.length, outputFiles.length);

        for (int test = 0; test < inputFiles.length; test++) {
            File inputFile = inputFiles[test];
            File outputFile = outputFiles[test];
            String testName = FilenameUtils.removeExtension(outputFile.getName());
            File resultFile = new File(testDir, String.format("%s.res", testName));

            InputStream inputStream = new FileInputStream(inputFile);
            System.setIn(inputStream);

            PrintStream resultStream = new PrintStream(resultFile);
            System.setOut(resultStream);

            mainRun.run();

            boolean match = FileUtils.contentEqualsIgnoreEOL(outputFile, resultFile, null);
            Assertions.assertTrue(match);
        }
    }

    public static void runSingleTest(File directory, String testName, ThrowingRunnable mainFunction) throws Exception {
        File input = directory.listFiles(f -> f.getName().equals(testName + ".in"))[0];
        File output = directory.listFiles(f -> f.getName().equals(testName + ".out"))[0];
        File result = new File(directory, testName + ".res");

        InputStream inputStream = new FileInputStream(input);
        PrintStream resultStream = new PrintStream(result);

        System.setIn(inputStream);
        System.setOut(resultStream);

        mainFunction.run();

        boolean match = FileUtils.contentEqualsIgnoreEOL(output, result, null);
        Assertions.assertTrue(match);
    }

    public static File getTestDirectory(Class<?> klass) {
        String packageName = klass.getPackage().getName();
        String path = packageName.replace("dev.rizaldi.uhunt", "").replaceAll("\\.", "/");
        try {
            URI testURI = klass.getResource(path).toURI();
            return new File(testURI);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
