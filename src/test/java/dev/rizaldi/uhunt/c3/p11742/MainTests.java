package dev.rizaldi.uhunt.c3.p11742;

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
    public void nasher() throws Exception {
        TestFileHelper.runSingleTest(testDirectory, "nasher", Main::main);
    }

    @Test
    public void govufpb() throws Exception {
        TestFileHelper.runSingleTest(testDirectory, "govufpb", Main::main);
    }
}
