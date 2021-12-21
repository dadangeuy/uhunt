package dev.rizaldi.uhunt.c2.p499;

import dev.rizaldi.uhunt.helper.TestFileHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.io.File;

public class MainTests {
    private final File testDirectory = TestFileHelper.getTestDirectory(getClass());

    @Test
    @Timeout(3)
    public void sample() throws Exception {
        TestFileHelper.runSingleTest(testDirectory, "sample", Main::main);
    }

    @Test
    @Timeout(3)
    public void uva() throws Exception {
        TestFileHelper.runSingleTest(testDirectory, "uva", Main::main);
    }

    @Test
    @Timeout(3)
    public void debugster_1() throws Exception {
        TestFileHelper.runSingleTest(testDirectory, "debugster_1", Main::main);
    }

    @Test
    @Timeout(3)
    public void debugster_2() throws Exception {
        TestFileHelper.runSingleTest(testDirectory, "debugster_2", Main::main);
    }
}
