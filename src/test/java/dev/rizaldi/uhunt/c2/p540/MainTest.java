package dev.rizaldi.uhunt.c2.p540;

import dev.rizaldi.uhunt.helper.TestHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.io.File;

public class MainTest {
    private final File testDirectory = TestHelper.getDirectory(getClass());

    @Test
    @Timeout(3)
    public void sample() throws Exception {
        TestHelper.run(Main::main, testDirectory, "sample");
    }

    @Test
    @Timeout(3)
    public void morass() throws Exception {
        TestHelper.run(Main::main, testDirectory, "morass");
    }

    @Test
    @Timeout(3)
    public void brianfry713() throws Exception {
        TestHelper.run(Main::main, testDirectory, "brianfry713");
    }
}
