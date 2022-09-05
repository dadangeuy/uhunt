package dev.rizaldi.uhunt.c2.p11111;

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
    public void alberto_verdejo() throws Exception {
        TestHelper.run(Main::main, directory, "alberto_verdejo");
    }

    @Test
    @Timeout(3)
    public void ap_222() throws Exception {
        TestHelper.run(Main::main, directory, "ap_222");
    }

    @Test
    @Timeout(3)
    public void daniel13() throws Exception {
        TestHelper.run(Main::main, directory, "daniel13");
    }

    @Test
    @Timeout(3)
    public void uva() throws Exception {
        TestHelper.run(Main::main, directory, "uva");
    }
}
