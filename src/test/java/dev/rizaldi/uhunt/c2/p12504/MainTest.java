package dev.rizaldi.uhunt.c2.p12504;

import dev.rizaldi.uhunt.helper.TestHelper;
import org.junit.jupiter.api.Test;

import java.io.File;

public class MainTest {
    private final File directory = TestHelper.getDirectory(getClass());

    @Test
    public void sample() throws Exception {
        TestHelper.run(Main::main, directory, "sample");
    }

    @Test
    public void _16321() throws Exception {
        TestHelper.run(Main::main, directory, "16321");
    }

    @Test
    public void arash16() throws Exception {
        TestHelper.run(Main::main, directory, "arash16");
    }

    @Test
    public void morass() throws Exception {
        TestHelper.run(Main::main, directory, "morass");
    }
}
