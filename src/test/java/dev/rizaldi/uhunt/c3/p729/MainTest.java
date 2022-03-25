package dev.rizaldi.uhunt.c3.p729;

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
    public void anjupiter() throws Exception {
        TestHelper.run(Main::main, testDirectory, "anjupiter");
    }

    @Test
    public void zayed() throws Exception {
        TestHelper.run(Main::main, testDirectory, "zayed");
    }

    @Test
    public void sahidcseku() throws Exception {
        TestHelper.run(Main::main, testDirectory, "sahidcseku");
    }
}
