package dev.rizaldi.uhunt.c2.p12626;

import dev.rizaldi.uhunt.helper.TestHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.io.File;

public class MainTests {
    private final File directory = TestHelper.getDirectory(getClass());

    @Test
    @Timeout(1)
    public void sample() throws Exception {
        TestHelper.run(Main::main, directory, "sample");
    }

    @Test
    @Timeout(1)
    public void shams() throws Exception {
        TestHelper.run(Main::main, directory, "shams");
    }

    @Test
    @Timeout(1)
    public void nanda() throws Exception {
        TestHelper.run(Main::main, directory, "nanda");
    }

    @Test
    @Timeout(1)
    public void ak94() throws Exception {
        TestHelper.run(Main::main, directory, "ak94");
    }
}
