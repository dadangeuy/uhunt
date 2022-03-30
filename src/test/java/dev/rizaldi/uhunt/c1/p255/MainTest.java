package dev.rizaldi.uhunt.c1.p255;

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
    public void bryton() throws Exception {
        TestHelper.run(Main::main, directory, "bryton");
    }

    @Test
    @Timeout(3)
    public void the_madman() throws Exception {
        TestHelper.run(Main::main, directory, "the_madman");
    }

    @Test
    @Timeout(3)
    public void lujoselu98() throws Exception {
        TestHelper.run(Main::main, directory, "lujoselu98");
    }
}
