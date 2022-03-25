package dev.rizaldi.uhunt.c3.p725;

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
    public void udebug1() throws Exception {
        TestHelper.run(Main::main, testDirectory, "udebug_1");
    }

    @Test
    public void udebug2() throws Exception {
        TestHelper.run(Main::main, testDirectory, "udebug_2");
    }
}
