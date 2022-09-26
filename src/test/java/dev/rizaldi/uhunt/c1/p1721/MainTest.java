package dev.rizaldi.uhunt.c1.p1721;

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
    public void metaphysis() throws Exception {
        TestHelper.run(Main::main, directory, "metaphysis");
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

    @Test
    @Timeout(3)
    public void txomin2091_3() throws Exception {
        TestHelper.run(Main::main, directory, "txomin2091_3");
    }

    @Test
    @Timeout(3)
    public void txomin2091_4() throws Exception {
        TestHelper.run(Main::main, directory, "txomin2091_4");
    }

    @Test
    @Timeout(3)
    public void txomin2091_5() throws Exception {
        TestHelper.run(Main::main, directory, "txomin2091_5");
    }

    @Test
    @Timeout(3)
    public void zex() throws Exception {
        TestHelper.run(Main::main, directory, "zex");
    }

    @Test
    @Timeout(3)
    public void rizaldi() throws Exception {
        TestHelper.run(Main::main, directory, "rizaldi");
    }
}
