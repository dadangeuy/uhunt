package dev.rizaldi.uhunt.c3.p11832;

import dev.rizaldi.uhunt.helper.TestHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.io.File;

public class MainTest {
    private final File directory = TestHelper.getDirectory(getClass());

    @Test
    @Timeout(5)
    public void sample() throws Exception {
        TestHelper.run(Main::main, directory, "sample");
    }

    @Test
    @Timeout(5)
    public void anjupiter() throws Exception {
        TestHelper.run(Main::main, directory, "anjupiter");
    }

    @Test
    @Timeout(5)
    public void feodorv() throws Exception {
        TestHelper.run(Main::main, directory, "feodorv");
    }
}
