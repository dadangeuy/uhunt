package dev.rizaldi.uhunt.c2.p727;

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
    public void trycatchme() throws Exception {
        TestHelper.run(Main::main, directory, "trycatchme");
    }

    @Test
    @Timeout(3)
    public void debugster() throws Exception {
        TestHelper.run(Main::main, directory, "debugster");
    }

    @Test
    public void mycodeschool() throws Exception {
        TestHelper.run(Main::main, directory, "mycodeschool");
    }
}
