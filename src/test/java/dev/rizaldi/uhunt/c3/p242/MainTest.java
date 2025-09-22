package dev.rizaldi.uhunt.c3.p242;

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
    public void calrare() throws Exception {
        TestHelper.run(Main::main, testDirectory, "calrare");
    }

    @Test
    @Timeout(3)
    public void feodorv() throws Exception {
        TestHelper.run(Main::main, testDirectory, "feodorv");
    }

    @Test
    @Timeout(3)
    public void r() throws Exception {
        TestHelper.run(Main::main, testDirectory, "r_1", "r_2", "r_3");
    }
}
