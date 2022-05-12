package dev.rizaldi.uhunt.c1.p12608;

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
    public void batman() throws Exception {
        TestHelper.run(Main::main, directory, "batman");
    }

    @Test
    @Timeout(1)
    public void zex() throws Exception {
        TestHelper.run(Main::main, directory, "zex");
    }
}
