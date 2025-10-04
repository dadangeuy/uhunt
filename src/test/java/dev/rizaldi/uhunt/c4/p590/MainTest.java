package dev.rizaldi.uhunt.c4.p590;

import dev.rizaldi.uhunt.helper.TestHelper;
import org.junit.jupiter.api.Test;

import java.io.File;

public class MainTest {
    private final File directory = TestHelper.getDirectory(getClass());

    @Test
    public void sample() throws Exception {
        TestHelper.run(Main::main, directory, "sample");
    }

    @Test
    public void dteo() throws Exception {
        TestHelper.run(Main::main, directory, "dteo");
    }

    @Test
    public void skittles() throws Exception {
        TestHelper.run(Main::main, directory, "skittles");
    }

    @Test
    public void jddantes() throws Exception {
        TestHelper.run(Main::main, directory, "jddantes");
    }
}
