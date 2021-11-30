package dev.rizaldi.uhunt.c3.p10020;

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
    public void brianfry713() throws Exception {
        TestFileHelper.runSingleTest(testDirectory, "brianfry713", Main::main);
    }
}
