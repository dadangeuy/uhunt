package dev.rizaldi.uhunt.c4.p872;

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
    public void fabikw() throws Exception {
        TestHelper.run(Main::main, directory, "fabikw");
    }

    @Test
    @Timeout(3)
    public void anonymous() throws Exception {
        TestHelper.run(Main::main, directory, "anonymous");
    }

    @Test
    @Timeout(3)
    public void twyu() throws Exception {
        TestHelper.run(Main::main, directory, "twyu");
    }
}
