package dev.rizaldi.uhunt.c2.p11550;

import dev.rizaldi.uhunt.helper.TestHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.io.File;

public class MainTests {
    private final File directory = TestHelper.getDirectory(getClass());

    @Test
    @Timeout(1)
    public void sample() throws Exception {
        TestHelper.run(Main::main, directory, "sample");
    }

    @Test
    @Timeout(1)
    public void udebug1() throws Exception {
        TestHelper.run(Main::main, directory, "udebug_1");
    }

    @Test
    @Timeout(1)
    public void udebug2() throws Exception {
        TestHelper.run(Main::main, directory, "udebug_2");
    }
}
