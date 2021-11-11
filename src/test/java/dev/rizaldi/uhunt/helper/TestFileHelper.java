package dev.rizaldi.uhunt.helper;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.junit.jupiter.api.Assertions;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintStream;

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

            boolean match = FileUtils.contentEquals(outputFile, resultFile);
            Assertions.assertTrue(match);
        }
    }
}
