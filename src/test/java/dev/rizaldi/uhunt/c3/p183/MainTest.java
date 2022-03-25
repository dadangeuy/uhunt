package dev.rizaldi.uhunt.c3.p183;

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
    public void morass1() throws Exception {
        TestHelper.run(Main::main, testDirectory, "morass_1");
    }

    @Test
    @Timeout(3)
    public void morass2() throws Exception {
        TestHelper.run(Main::main, testDirectory, "morass_2");
    }
}
