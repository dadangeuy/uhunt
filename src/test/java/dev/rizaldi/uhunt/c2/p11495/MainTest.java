package dev.rizaldi.uhunt.c2.p11495;

import dev.rizaldi.uhunt.helper.TestHelper;
import org.junit.jupiter.api.Test;

import java.io.File;

public class MainTest {
    private final File directory = TestHelper.getDirectory(getClass());

    @Test
    public void sample() throws Exception {
        TestHelper.run(Main::main, directory, "sample");
    }

    @Test
    public void udebug() throws Exception {
        TestHelper.run(Main::main, directory, "udebug");
    }
}
