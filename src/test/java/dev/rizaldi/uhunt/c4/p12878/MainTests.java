package dev.rizaldi.uhunt.c4.p12878;

import dev.rizaldi.uhunt.helper.TestHelper;
import org.junit.jupiter.api.Test;

import java.io.File;

public class MainTests {
    private final File testDirectory = TestHelper.getDirectory(getClass());

    @Test
    public void sample() throws Exception {
        TestHelper.run(Main::main, testDirectory, "sample");
    }

    @Test
    public void alberto_verdejo_1() throws Exception {
        TestHelper.run(Main::main, testDirectory, "alberto.verdejo.1");
    }

    @Test
    public void alberto_verdejo_2() throws Exception {
        TestHelper.run(Main::main, testDirectory, "alberto.verdejo.2");
    }

    @Test
    public void alberto_verdejo_3() throws Exception {
        TestHelper.run(Main::main, testDirectory, "alberto.verdejo.3");
    }
}
