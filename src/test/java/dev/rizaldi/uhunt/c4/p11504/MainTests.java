package dev.rizaldi.uhunt.c4.p11504;

import dev.rizaldi.uhunt.helper.TestHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.io.File;

public class MainTests {
    private final File directory = TestHelper.getDirectory(getClass());

    @Test
    @Timeout(3)
    public void sample() throws Exception {
        TestHelper.run(Main::main, directory, "sample");
    }

    @Test
    @Timeout(3)
    public void morass() throws Exception {
        TestHelper.run(Main::main, directory, "morass");
    }

    @Test
    @Timeout(3)
    public void brianfry713() throws Exception {
        TestHelper.run(Main::main, directory, "brianfry713");
    }

    @Test
    @Timeout(3)
    public void rizaldi() throws Exception {
        TestHelper.run(Main::main, directory, "rizaldi");
    }

    @Test
    @Timeout(3)
    public void kazi() throws Exception {
        TestHelper.run(Main::main, directory, "kazi");
    }

    @Test
    @Timeout(3)
    public void taher144() throws Exception {
        TestHelper.run(Main::main, directory, "taher144");
    }

    @Test
    @Timeout(3)
    public void magicfirebolt() throws Exception {
        TestHelper.run(Main::main, directory, "magicfirebolt");
    }

    @Test
    @Timeout(3)
    public void mdashif313() throws Exception {
        TestHelper.run(Main::main, directory, "mdashif313");
    }

    @Test
    @Timeout(3)
    public void gabrc52() throws Exception {
        TestHelper.run(Main::main, directory, "gabrc52");
    }
}
