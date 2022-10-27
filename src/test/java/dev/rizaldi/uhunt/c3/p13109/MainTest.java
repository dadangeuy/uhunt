package dev.rizaldi.uhunt.c3.p13109;

import dev.rizaldi.uhunt.helper.TestHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.io.File;

public class MainTest {
    private final File directory = TestHelper.getDirectory(getClass());

    @Test
    @Timeout(2)
    public void sample() throws Exception {
        TestHelper.run(Main::main, directory, "sample");
    }

    @Test
    @Timeout(2)
    public void anjupiter() throws Exception {
        TestHelper.run(Main::main, directory, "anjupiter");
    }

    @Test
    @Timeout(2)
    public void ak94() throws Exception {
        TestHelper.run(Main::main, directory, "ak94");
    }

    @Test
    @Timeout(2)
    public void ajunior() throws Exception {
        TestHelper.run(Main::main, directory, "ajunior");
    }
}
