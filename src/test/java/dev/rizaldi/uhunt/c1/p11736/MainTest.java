package dev.rizaldi.uhunt.c1.p11736;

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
    public void s6088() throws Exception {
        TestHelper.run(Main::main, directory, "s6088");
    }

    @Test
    @Timeout(1)
    public void zex() throws Exception {
        TestHelper.run(Main::main, directory, "zex");
    }
}
