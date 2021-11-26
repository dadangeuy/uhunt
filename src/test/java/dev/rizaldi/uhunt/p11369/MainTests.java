package dev.rizaldi.uhunt.p11369;

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
    public void morass() throws Exception {
        TestFileHelper.runSingleTest(testDirectory, "morass", Main::main);
    }

    @Test
    public void pedropablo() throws Exception {
        TestFileHelper.runSingleTest(testDirectory, "pedropablo", Main::main);
    }
}
