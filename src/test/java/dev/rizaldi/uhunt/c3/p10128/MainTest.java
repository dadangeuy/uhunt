package dev.rizaldi.uhunt.c3.p10128;

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
}
