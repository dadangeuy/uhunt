package dev.rizaldi.uhunt.c4.p260;

import dev.rizaldi.uhunt.helper.TestHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.io.File;

public class MainTest {
    private final File testDirectory = TestHelper.getDirectory(getClass());

    @Test
    @Timeout(3)
    public void sample() throws Exception {
        TestHelper.run(Main::main, testDirectory, "sample");
    }

    @Test
    @Timeout(3)
    public void brianfry() throws Exception {
        TestHelper.run(Main::main, testDirectory, "brianfry");
    }

    @Test
    @Timeout(3)
    public void twyu() throws Exception {
        TestHelper.run(Main::main, testDirectory, "twyu");
    }

    @Test
    @Timeout(3)
    public void nakar() throws Exception {
        TestHelper.run(Main::main, testDirectory, "nakar");
    }
}
