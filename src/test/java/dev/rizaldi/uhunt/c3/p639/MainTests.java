package dev.rizaldi.uhunt.c3.p639;

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
    public void dread_pirate() throws Exception {
        TestHelper.run(Main::main, directory, "dread_pirate");
    }

    @Test
    @Timeout(3)
    public void shameem_alam() throws Exception {
        TestHelper.run(Main::main, directory, "shameem_alam");
    }

    @Test
    @Timeout(3)
    public void uva() throws Exception {
        TestHelper.run(Main::main, directory, "uva");
    }
}
