package dev.rizaldi.uhunt.c2.p11997;

import dev.rizaldi.uhunt.helper.TestHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.io.File;

public class MainTest {
    private final File testDirectory = TestHelper.getDirectory(getClass());

    @Test
    @Timeout(1)
    public void case1() throws Exception {
        TestHelper.run(Main::main, testDirectory, "1");
    }

    @Test
    @Timeout(1)
    public void case2() throws Exception {
        TestHelper.run(Main::main, testDirectory, "2");
    }

    @Test
    @Timeout(1)
    public void case3() throws Exception {
        TestHelper.run(Main::main, testDirectory, "3");
    }
}
