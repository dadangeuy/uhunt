package dev.rizaldi.uhunt.c2.p11360;

import dev.rizaldi.uhunt.helper.TestHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.io.File;

public class MainTest {
    private final File directory = TestHelper.getDirectory(getClass());

    @Test
    @Timeout(2)
    public void sample() throws Exception {
        TestHelper.run(Main::main, directory, "sample");
    }

    @Test
    @Timeout(2)
    public void morass() throws Exception {
        TestHelper.run(Main::main, directory, "morass");
    }

    @Test
    @Timeout(2)
    public void brianfry713() throws Exception {
        TestHelper.run(Main::main, directory, "brianfry713");
    }
}
