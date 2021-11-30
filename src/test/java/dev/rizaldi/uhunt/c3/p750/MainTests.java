package dev.rizaldi.uhunt.c3.p750;

import dev.rizaldi.uhunt.helper.TestFileHelper;
import org.junit.jupiter.api.Test;

import java.io.File;

public class MainTests {
    private final File testDirectory = TestFileHelper.getTestDirectory(getClass());

    @Test
    public void example1() throws Exception {
        TestFileHelper.runSingleTest(testDirectory, "1", Main::main);
    }

    @Test
    public void example2() throws Exception {
        TestFileHelper.runSingleTest(testDirectory, "2", Main::main);
    }
}
