package dev.rizaldi.uhunt.c2.p11402;

import dev.rizaldi.uhunt.helper.TestHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.io.File;

public class MainTest {
    private final File directory = TestHelper.getDirectory(getClass());

    @Test
    @Timeout(5)
    public void sample() throws Exception {
        TestHelper.run(Main::main, directory, "sample");
    }

    @Test
    @Timeout(5)
    public void brianfry713() throws Exception {
        TestHelper.run(Main::main, directory, "brianfry713");
    }

    @Test
    @Timeout(5)
    public void morass() throws Exception {
        TestHelper.run(Main::main, directory, "morass");
    }

    @Test
    @Timeout(5)
    public void robbinb1993() throws Exception {
        TestHelper.run(Main::main, directory, "robbinb1993");
    }

    @Test
    @Timeout(5)
    public void rrrrrrrr() throws Exception {
        TestHelper.run(Main::main, directory, "rrrrrrrr");
    }
}
