package dev.rizaldi.uhunt.c4.p247;

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
    public void rizaldi() throws Exception {
        TestHelper.run(Main::main, testDirectory, "rizaldi");
    }
}
