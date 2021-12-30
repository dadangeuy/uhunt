package dev.rizaldi.uhunt.c3.p12390;

import dev.rizaldi.uhunt.helper.TestHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.io.File;

public class MainTests {
    private final File testDirectory = TestHelper.getDirectory(getClass());

    @Test
    @Timeout(4)
    public void sample() throws Exception {
        TestHelper.run(Main::main, testDirectory, "sample");
    }

    @Test
    @Timeout(4)
    public void anjupiter() throws Exception {
        TestHelper.run(Main::main, testDirectory, "anjupiter");
    }

    @Test
    @Timeout(4)
    public void morass() throws Exception {
        TestHelper.run(Main::main, testDirectory, "morass");
    }
}
