package dev.rizaldi.uhunt.c2.p12187;

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
    public void alberto_verdejo() throws Exception {
        TestHelper.run(Main::main, directory, "alberto_verdejo");
    }

    @Test
    public void feodorv() throws Exception {
        TestHelper.run(Main::main, directory, "feodorv");
    }

    @Test
    public void udebug() throws Exception {
        TestHelper.run(Main::main, directory, "udebug");
    }
}
