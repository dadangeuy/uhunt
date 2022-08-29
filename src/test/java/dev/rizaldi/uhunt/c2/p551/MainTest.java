package dev.rizaldi.uhunt.c2.p551;

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
    public void fockdislaif() throws Exception {
        TestHelper.run(Main::main, directory, "fockdislaif");
    }

    @Test
    @Timeout(3)
    public void matheus_aguilar() throws Exception {
        TestHelper.run(Main::main, directory, "matheus_aguilar");
    }

    @Test
    @Timeout(3)
    public void morass() throws Exception {
        TestHelper.run(Main::main, directory, "morass");
    }

    @Test
    @Timeout(3)
    public void uva() throws Exception {
        TestHelper.run(Main::main, directory, "uva");
    }
}
