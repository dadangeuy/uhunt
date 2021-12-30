package dev.rizaldi.uhunt.c4.p11463;

import dev.rizaldi.uhunt.helper.TestHelper;
import org.junit.jupiter.api.Test;

import java.io.File;

public class MainTests {
    private final File testDirectory = TestHelper.getDirectory(getClass());

    @Test
    public void sample() throws Exception {
        TestHelper.run(Main::main, testDirectory, "sample");
    }

    @Test
    public void batman() throws Exception {
        TestHelper.run(Main::main, testDirectory, "batman");
    }

    @Test
    public void uva() throws Exception {
        TestHelper.run(Main::main, testDirectory, "uva");
    }
}
