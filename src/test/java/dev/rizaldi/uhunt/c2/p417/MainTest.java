package dev.rizaldi.uhunt.c2.p417;

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
    public void feodorv() throws Exception {
        TestHelper.run(Main::main, directory, "feodorv");
    }

    @Test
    @Timeout(3)
    public void nakar81() throws Exception {
        TestHelper.run(Main::main, directory, "nakar81");
    }

    @Test
    @Timeout(3)
    public void shah_shishir() throws Exception {
        TestHelper.run(Main::main, directory, "shah_shishir");
    }

    @Test
    @Timeout(3)
    public void udebug() throws Exception {
        TestHelper.run(Main::main, directory, "udebug");
    }
}
