package dev.rizaldi.uhunt.p725;

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
    public void udebug1() throws Exception {
        TestFileHelper.runSingleTest(testDirectory, "udebug_1", Main::main);
    }

    @Test
    public void udebug2() throws Exception {
        TestFileHelper.runSingleTest(testDirectory, "udebug_2", Main::main);
    }
}
