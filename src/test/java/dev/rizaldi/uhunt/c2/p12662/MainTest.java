package dev.rizaldi.uhunt.c2.p12662;

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
    public void alberto_verdejo_1() throws Exception {
        TestHelper.run(Main::main, directory, "alberto_verdejo_1");
    }

    @Test
    @Timeout(1)
    public void alberto_verdejo_2() throws Exception {
        TestHelper.run(Main::main, directory, "alberto_verdejo_2");
    }

    @Test
    @Timeout(1)
    public void goodeath_1() throws Exception {
        TestHelper.run(Main::main, directory, "goodeath_1");
    }

    @Test
    @Timeout(1)
    public void goodeath_2() throws Exception {
        TestHelper.run(Main::main, directory, "goodeath_2");
    }
}
