package dev.rizaldi.uhunt.c2.p11987;

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
    public void uva() throws Exception {
        TestHelper.run(Main::main, directory, "uva");
    }

    @Test
    @Timeout(1)
    public void morass() throws Exception {
        TestHelper.run(Main::main, directory, "morass");
    }

    @Test
    @Timeout(1)
    public void twyu() throws Exception {
        TestHelper.run(Main::main, directory, "twyu");
    }

    @Test
    @Timeout(1)
    public void alberto() throws Exception {
        TestHelper.run(Main::main, directory, "alberto");
    }

    @Test
    @Timeout(1)
    public void alberto2() throws Exception {
        TestHelper.run(Main::main, directory, "alberto2");
    }

    @Test
//    @Timeout(1)
    public void oremor() throws Exception {
        TestHelper.run(Main::main, directory, "oremor");
    }
}
