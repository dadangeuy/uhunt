package dev.rizaldi.uhunt.c4.p200;

import dev.rizaldi.uhunt.helper.TestHelper;
import org.junit.jupiter.api.Test;

import java.io.File;

public class MainTest {
    private final File testDirectory = TestHelper.getDirectory(getClass());

    @Test
    public void example1() throws Exception {
        TestHelper.run(Main::main, testDirectory, "1");
    }

    @Test
    public void example2() throws Exception {
        TestHelper.run(Main::main, testDirectory, "2");
    }

    @Test
    public void example3() throws Exception {
        TestHelper.run(Main::main, testDirectory, "3");
    }
}
