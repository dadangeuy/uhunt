package dev.rizaldi.uhunt.c2.p1610;

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
    public void anacharsis_1() throws Exception {
        TestHelper.run(Main::main, directory, "anacharsis_1");
    }

    @Test
    @Timeout(3)
    public void anacharsis_2() throws Exception {
        TestHelper.run(Main::main, directory, "anacharsis_2");
    }

    @Test
    @Timeout(3)
    public void txomin2091_1() throws Exception {
        TestHelper.run(Main::main, directory, "txomin2091_1");
    }

    @Test
    @Timeout(3)
    public void txomin2091_2() throws Exception {
        TestHelper.run(Main::main, directory, "txomin2091_2");
    }
}
