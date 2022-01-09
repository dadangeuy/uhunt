package dev.rizaldi.uhunt.c2.p10608;

import dev.rizaldi.uhunt.helper.TestHelper;
import org.junit.jupiter.api.Test;

import java.io.File;

public class MainTests {
    private final File testDirectory = TestHelper.getDirectory(getClass());

    @Test
    public void case1() throws Exception {
        TestHelper.run(Main::main, testDirectory, "1");
    }
}
