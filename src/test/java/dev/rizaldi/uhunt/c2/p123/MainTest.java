package dev.rizaldi.uhunt.c2.p123;

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
    public void morris821028() throws Exception {
        TestHelper.run(Main::main, directory, "morris821028");
    }

    @Test
    @Timeout(3)
    public void bryton() throws Exception {
        TestHelper.run(Main::main, directory, "bryton");
    }
}
