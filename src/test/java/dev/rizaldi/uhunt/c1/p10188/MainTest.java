package dev.rizaldi.uhunt.c1.p10188;

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
    public void r19() throws Exception {
        TestHelper.run(Main::main, directory, "r19");
    }

    @Test
    @Timeout(3)
    public void matheus_aguilar() throws Exception {
        TestHelper.run(Main::main, directory, "matheus_aguilar");
    }
}
