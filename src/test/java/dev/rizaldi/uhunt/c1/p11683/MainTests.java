package dev.rizaldi.uhunt.c1.p11683;

import dev.rizaldi.uhunt.helper.TestHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.io.File;

public class MainTests {
    private final File directory = TestHelper.getDirectory(getClass());

    @Test
    @Timeout(1)
    public void sample() throws Exception {
        TestHelper.run(Main::main, directory, "sample");
    }

    @Test
    @Timeout(1)
    public void mmello() throws Exception {
        TestHelper.run(Main::main, directory, "mmello");
    }
}
