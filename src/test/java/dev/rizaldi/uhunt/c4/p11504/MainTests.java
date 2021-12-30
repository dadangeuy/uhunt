package dev.rizaldi.uhunt.c4.p11504;

import dev.rizaldi.uhunt.helper.TestFileHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.io.File;

public class MainTests {
    private final File directory = TestFileHelper.getTestDirectory(getClass());

    @Test
    @Timeout(3)
    public void sample() throws Exception {
        TestFileHelper.runSingleTest(directory, "sample", Main::main);
    }

    @Test
    @Timeout(3)
    public void morass() throws Exception {
        TestFileHelper.runSingleTest(directory, "morass", Main::main);
    }

    @Test
    @Timeout(3)
    public void brianfry713() throws Exception {
        TestFileHelper.runSingleTest(directory, "brianfry713", Main::main);
    }

    @Test
    @Timeout(3)
    public void rizaldi() throws Exception {
        TestFileHelper.runSingleTest(directory, "rizaldi", Main::main);
    }

    @Test
    @Timeout(3)
    public void kazi() throws Exception {
        TestFileHelper.runSingleTest(directory, "kazi", Main::main);
    }

    @Test
    @Timeout(3)
    public void taher144() throws Exception {
        TestFileHelper.runSingleTest(directory, "taher144", Main::main);
    }

    @Test
    @Timeout(3)
    public void magicfirebolt() throws Exception {
        TestFileHelper.runSingleTest(directory, "magicfirebolt", Main::main);
    }

    @Test
    @Timeout(3)
    public void mdashif313() throws Exception {
        TestFileHelper.runSingleTest(directory, "mdashif313", Main::main);
    }

    @Test
    @Timeout(3)
    public void gabrc52() throws Exception {
        TestFileHelper.runSingleTest(directory, "gabrc52", Main::main);
    }
}
