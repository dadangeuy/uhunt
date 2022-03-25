package dev.rizaldi.uhunt.c2.p12049;

import dev.rizaldi.uhunt.helper.TestHelper;
import org.junit.jupiter.api.Test;

import java.io.File;

public class MainTest {
    private final File directory = TestHelper.getDirectory(getClass());

    @Test
    public void fileTest() throws Exception {
        TestHelper.run(Main::main, directory, "1", "2", "3");
    }
}
