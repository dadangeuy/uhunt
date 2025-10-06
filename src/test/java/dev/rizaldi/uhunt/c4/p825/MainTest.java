package dev.rizaldi.uhunt.c4.p825;

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
    public void uva() throws Exception {
        TestHelper.run(Main::main, directory, "uva");
    }

    @Test
    public void amiratou() throws Exception {
        TestHelper.run(Main::main, directory, "amiratou");
    }

    @Test
    public void alamkhan() throws Exception {
        TestHelper.run(Main::main, directory, "alamkhan");
    }
}
