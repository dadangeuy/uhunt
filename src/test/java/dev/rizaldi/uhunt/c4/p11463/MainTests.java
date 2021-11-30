package dev.rizaldi.uhunt.c4.p11463;

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
    public void batman() throws Exception {
        TestFileHelper.runSingleTest(testDirectory, "batman", Main::main);
    }

    @Test
    public void uva() throws Exception {
        TestFileHelper.runSingleTest(testDirectory, "uva", Main::main);
    }
}
