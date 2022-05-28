package dev.rizaldi.uhunt.c2.p12650;

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
    public void morass_1() throws Exception {
        TestHelper.run(Main::main, directory, "morass_1");
    }

    @Test
    @Timeout(1)
    public void morass_2() throws Exception {
        TestHelper.run(Main::main, directory, "morass_2");
    }

    @Test
    @Timeout(1)
    public void feodorv() throws Exception {
        TestHelper.run(Main::main, directory, "feodorv");
    }
}
