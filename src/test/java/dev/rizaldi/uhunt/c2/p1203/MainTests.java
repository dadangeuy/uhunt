package dev.rizaldi.uhunt.c2.p1203;

import dev.rizaldi.uhunt.helper.TestHelper;
import org.junit.jupiter.api.Test;

import java.io.File;

public class MainTests {
    private final File directory = TestHelper.getDirectory(getClass());

    @Test
    public void example() throws Exception {
        TestHelper.run(Main::main, directory, "1");
    }
}
