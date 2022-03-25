package dev.rizaldi.uhunt.c3.p10020;

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
    public void morass() throws Exception {
        TestHelper.run(Main::main, testDirectory, "morass");
    }

    @Test
    public void brianfry713() throws Exception {
        TestHelper.run(Main::main, testDirectory, "brianfry713");
    }
}
