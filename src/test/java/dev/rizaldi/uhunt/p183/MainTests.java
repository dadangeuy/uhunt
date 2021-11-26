package dev.rizaldi.uhunt.p183;

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
    public void morass1() throws Exception {
        TestFileHelper.runSingleTest(testDirectory, "morass_1", Main::main);
    }

    @Test
    public void morass2() throws Exception {
        TestFileHelper.runSingleTest(testDirectory, "morass_2", Main::main);
    }
}
