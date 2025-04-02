package dev.rizaldi.uhunt.c2.p10703;

import dev.rizaldi.uhunt.helper.TestHelper;
import org.junit.jupiter.api.Test;

import java.io.File;

public class MainTest {
    private final File testDirectory = TestHelper.getDirectory(getClass());

    @Test
    public void sample() throws Exception {
        TestHelper.run(Main::main, testDirectory, "sample");
    }

    @Test
    public void udebug() throws Exception {
        TestHelper.run(Main::main, testDirectory, "udebug");
    }

    @Test
    public void turin101() throws Exception {
        TestHelper.run(Main::main, testDirectory, "turin101");
    }
}
