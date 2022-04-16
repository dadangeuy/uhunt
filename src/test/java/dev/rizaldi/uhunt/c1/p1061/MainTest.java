package dev.rizaldi.uhunt.c1.p1061;

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
    public void antonyd() throws Exception {
        TestHelper.run(Main::main, directory, "antonyd");
    }

    @Test
    @Timeout(3)
    public void uva() throws Exception {
        TestHelper.run(Main::main, directory, "uva");
    }

    @Test
    @Timeout(3)
    public void ebb() throws Exception {
        TestHelper.run(Main::main, directory, "ebb");
    }
}
