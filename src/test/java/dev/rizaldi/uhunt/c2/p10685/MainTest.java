package dev.rizaldi.uhunt.c2.p10685;

import dev.rizaldi.uhunt.helper.TestHelper;
import org.junit.jupiter.api.Test;

import java.io.File;

public class MainTest {
    private final File testDirectory = TestHelper.getDirectory(getClass());

    @Test
    public void sample() throws Exception {
        TestHelper.run(Main::main, testDirectory, "sample");
    }

    @Test
    public void debugster() throws Exception {
        TestHelper.run(Main::main, testDirectory, "debugster");
    }

    @Test
    public void hallway() throws Exception {
        TestHelper.run(Main::main, testDirectory, "hallway");
    }

    @Test
    public void md_ashif313() throws Exception {
        TestHelper.run(Main::main, testDirectory, "md.ashif313");
    }

    @Test
    public void morass() throws Exception {
        TestHelper.run(Main::main, testDirectory, "morass");
    }

    @Test
    public void uva() throws Exception {
        TestHelper.run(Main::main, testDirectory, "uva");
    }
}
