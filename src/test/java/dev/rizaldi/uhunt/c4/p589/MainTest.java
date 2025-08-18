package dev.rizaldi.uhunt.c4.p589;

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
    public void rizaldi() throws Exception {
        TestHelper.run(Main::main, directory, "rizaldi");
    }

    @Test
    public void uva() throws Exception {
        TestHelper.run(Main::main, directory, "uva");
    }

    @Test
    public void feodorv() throws Exception {
        TestHelper.run(Main::main, directory, "feodorv");
    }

    @Test
    public void txomin() throws Exception {
        TestHelper.run(Main::main, directory, "txomin");
    }
}
