package dev.rizaldi.uhunt.c1.p10646;

import dev.rizaldi.uhunt.helper.TestHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.io.File;

public class MainTest {
    private final File directory = TestHelper.getDirectory(getClass());

    @Test
    @Timeout(3)
    public void sample() throws Exception {
        TestHelper.run(Main::main, directory, "sample");
    }

    @Test
    @Timeout(3)
    public void uva() throws Exception {
        TestHelper.run(Main::main, directory, "uva");
    }

    @Test
    @Timeout(3)
    public void morass_1() throws Exception {
        TestHelper.run(Main::main, directory, "morass_1");
    }

    @Test
    @Timeout(3)
    public void morass_2() throws Exception {
        TestHelper.run(Main::main, directory, "morass_2");
    }

    @Test
    @Timeout(3)
    public void the_madman() throws Exception {
        TestHelper.run(Main::main, directory, "the_madman");
    }
}
