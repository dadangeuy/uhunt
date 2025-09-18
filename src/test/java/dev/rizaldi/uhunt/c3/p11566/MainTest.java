package dev.rizaldi.uhunt.c3.p11566;

import dev.rizaldi.uhunt.helper.TestHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.io.File;

public class MainTest {
    private final File directory = TestHelper.getDirectory(getClass());

    @Test
    @Timeout(1)
    public void sample() throws Exception {
        TestHelper.run(Main::main, directory, "sample");
    }

    @Test
    @Timeout(1)
    public void brianfry() throws Exception {
        TestHelper.run(Main::main, directory, "brianfry");
    }

    @Test
    @Timeout(1)
    public void lennart() throws Exception {
        TestHelper.run(Main::main, directory, "lennart");
    }
}
