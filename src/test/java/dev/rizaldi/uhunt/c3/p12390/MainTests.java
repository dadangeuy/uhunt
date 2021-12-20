package dev.rizaldi.uhunt.c3.p12390;

import dev.rizaldi.uhunt.helper.TestFileHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.io.File;

public class MainTests {
    private final File testDirectory = TestFileHelper.getTestDirectory(getClass());

    @Test
    @Timeout(4)
    public void sample() throws Exception {
        TestFileHelper.runSingleTest(testDirectory, "sample", Main::main);
    }

    @Test
    @Timeout(4)
    public void anjupiter() throws Exception {
        TestFileHelper.runSingleTest(testDirectory, "anjupiter", Main::main);
    }

    @Test
    @Timeout(4)
    public void morass() throws Exception {
        TestFileHelper.runSingleTest(testDirectory, "morass", Main::main);
    }
}
