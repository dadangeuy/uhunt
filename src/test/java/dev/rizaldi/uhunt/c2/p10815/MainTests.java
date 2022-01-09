package dev.rizaldi.uhunt.c2.p10815;

import dev.rizaldi.uhunt.helper.TestHelper;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.URISyntaxException;

public class MainTests {
    private final File testDirectory = TestHelper.getDirectory(getClass());

    public MainTests() throws URISyntaxException {
    }

    @Test
    public void case1() throws Exception {
        TestHelper.run(Main::main, testDirectory, "1");
    }

    @Test
    public void case2() throws Exception {
        TestHelper.run(Main::main, testDirectory, "2");
    }

}
