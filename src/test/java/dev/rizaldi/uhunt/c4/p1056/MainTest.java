package dev.rizaldi.uhunt.c4.p1056;

import dev.rizaldi.uhunt.helper.TestHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.io.File;

public class MainTest {
    private static final int TIMEOUT = 3;
    private final File directory = TestHelper.getDirectory(getClass());

    @Test
    @Timeout(TIMEOUT)
    public void sample() throws Exception {
        TestHelper.run(Main::main, directory, "sample");
    }

    @Test
    @Timeout(TIMEOUT)
    public void anjupiter() throws Exception {
        TestHelper.run(Main::main, directory, "anjupiter");
    }

    @Test
    @Timeout(TIMEOUT)
    public void batman() throws Exception {
        TestHelper.run(Main::main, directory, "batman");
    }
}
