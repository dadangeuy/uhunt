package dev.rizaldi.uhunt.c3.p307;

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
    public void szp() throws Exception {
        TestHelper.run(Main::main, testDirectory, "szp");
    }

    @Test
    public void uva() throws Exception {
        TestHelper.run(Main::main, testDirectory, "uva");
    }

    @Test
    public void rizaldi() throws Exception {
        TestHelper.run(Main::main, testDirectory, "rizaldi");
    }
}
