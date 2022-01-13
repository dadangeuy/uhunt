package dev.rizaldi.uhunt.c4.p12160;

import dev.rizaldi.uhunt.helper.TestHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.io.File;

public class MainTests {
    private final File directory = TestHelper.getDirectory(getClass());

    @Test
    @Timeout(3)
    public void sample() throws Exception {
        TestHelper.run(Main::main, directory, "sample");
    }

    @Test
    @Timeout(3)
    public void morass() throws Exception {
        TestHelper.run(Main::main, directory, "morass");
    }

    @Test
    @Timeout(3)
    public void nasher() throws Exception {
        TestHelper.run(Main::main, directory, "nasher");
    }

    @Test
    @Timeout(3)
    public void turin101_1() throws Exception {
        TestHelper.run(Main::main, directory, "turin101_1");
    }

    @Test
    @Timeout(3)
    public void turin101_2() throws Exception {
        TestHelper.run(Main::main, directory, "turin101_2");
    }
}
