package dev.rizaldi.uhunt.c3.p674;

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
    public void uva() throws Exception {
        TestFileHelper.runSingleTest(testDirectory, "uva", Main::main);
    }

    @Test
    public void feodorv() throws Exception {
        TestFileHelper.runSingleTest(testDirectory, "feodorv", Main::main);
    }
}
