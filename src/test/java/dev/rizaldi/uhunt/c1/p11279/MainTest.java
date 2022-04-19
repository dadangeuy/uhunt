package dev.rizaldi.uhunt.c1.p11279;

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
    public void bryton() throws Exception {
        TestHelper.run(Main::main, directory, "bryton");
    }

    @Test
    @Timeout(2)
    public void zex() throws Exception {
        TestHelper.run(Main::main, directory, "zex");
    }
}
