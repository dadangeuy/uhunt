package dev.rizaldi.uhunt.c2.p12299;

import dev.rizaldi.uhunt.helper.TestHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.io.File;

public class MainTest {
    private final File directory = TestHelper.getDirectory(getClass());

    @Test
    @Timeout(1)
    public void sample() throws Exception {
        TestHelper.run(Main::main, directory, "sample");
    }

    @Test
    @Timeout(1)
    public void anjupiter() throws Exception {
        TestHelper.run(Main::main, directory, "anjupiter");
    }

    @Test
    @Timeout(1)
    public void rrrrrrrr() throws Exception {
        TestHelper.run(Main::main, directory, "rrrrrrrr");
    }
}
