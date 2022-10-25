package dev.rizaldi.uhunt.c3.p11900;

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
    public void udebug() throws Exception {
        TestHelper.run(Main::main, directory, "udebug");
    }

    @Test
    @Timeout(1)
    public void debugster() throws Exception {
        TestHelper.run(Main::main, directory, "debugster");
    }

    @Test
    @Timeout(1)
    public void plabon022() throws Exception {
        TestHelper.run(Main::main, directory, "plabon022");
    }

    @Test
    @Timeout(1)
    public void ak94() throws Exception {
        TestHelper.run(Main::main, directory, "ak94");
    }
}
