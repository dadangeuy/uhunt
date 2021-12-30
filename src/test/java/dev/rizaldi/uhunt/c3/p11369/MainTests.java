package dev.rizaldi.uhunt.c3.p11369;

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
    public void morass() throws Exception {
        TestHelper.run(Main::main, testDirectory, "morass");
    }

    @Test
    public void pedropablo() throws Exception {
        TestHelper.run(Main::main, testDirectory, "pedropablo");
    }
}
