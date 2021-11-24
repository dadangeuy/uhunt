package dev.rizaldi.uhunt.p729;

import dev.rizaldi.uhunt.helper.TestFileHelper;
import org.junit.jupiter.api.Test;

import java.io.File;

public class MainTests {
    private final File testDirectory = TestFileHelper.getTestDirectory(getClass());

    @Test
    public void sample() throws Exception {
        TestFileHelper.runSingleTest(testDirectory, "sample", Main::main);
    }

    @Test
    public void anjupiter() throws Exception {
        TestFileHelper.runSingleTest(testDirectory, "anjupiter", Main::main);
    }

    @Test
    public void zayed() throws Exception {
        TestFileHelper.runSingleTest(testDirectory, "zayed", Main::main);
    }

    @Test
    public void sahidcseku() throws Exception {
        TestFileHelper.runSingleTest(testDirectory, "sahidcseku", Main::main);
    }
}
