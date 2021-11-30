package dev.rizaldi.uhunt.c2.p10685;

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
    public void debugster() throws Exception {
        TestFileHelper.runSingleTest(testDirectory, "debugster", Main::main);
    }

    @Test
    public void hallway() throws Exception {
        TestFileHelper.runSingleTest(testDirectory, "hallway", Main::main);
    }

    @Test
    public void md_ashif313() throws Exception {
        TestFileHelper.runSingleTest(testDirectory, "md.ashif313", Main::main);
    }

    @Test
    public void morass() throws Exception {
        TestFileHelper.runSingleTest(testDirectory, "morass", Main::main);
    }

    @Test
    public void uva() throws Exception {
        TestFileHelper.runSingleTest(testDirectory, "uva", Main::main);
    }
}
