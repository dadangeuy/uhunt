package dev.rizaldi.uhunt.c4.p12878;

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
    public void alberto_verdejo_1() throws Exception {
        TestFileHelper.runSingleTest(testDirectory, "alberto.verdejo.1", Main::main);
    }

    @Test
    public void alberto_verdejo_2() throws Exception {
        TestFileHelper.runSingleTest(testDirectory, "alberto.verdejo.2", Main::main);
    }

    @Test
    public void alberto_verdejo_3() throws Exception {
        TestFileHelper.runSingleTest(testDirectory, "alberto.verdejo.3", Main::main);
    }
}
