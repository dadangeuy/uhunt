package dev.rizaldi.uhunt.c2.p1513;

import dev.rizaldi.uhunt.helper.TestHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.io.File;

public class MainTest {
    private final File directory = TestHelper.getDirectory(getClass());

    @Test
    @Timeout(3)
    public void sample() throws Exception {
        TestHelper.run(Main::main, directory, "sample");
    }

    @Test
    @Timeout(3)
    public void anjupiter() throws Exception {
        TestHelper.run(Main::main, directory, "anjupiter");
    }

    @Test
    @Timeout(3)
    public void pedropablo() throws Exception {
        TestHelper.run(Main::main, directory, "pedropablo");
    }
}
