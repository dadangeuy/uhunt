package dev.rizaldi.uhunt.p10815;

import dev.rizaldi.uhunt.helper.TestFileHelper;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.URISyntaxException;

public class MainTests {
    private final File testDirectory = TestFileHelper.getTestDirectory(getClass());

    public MainTests() throws URISyntaxException {
    }

    @Test
    public void case1() throws Exception {
        TestFileHelper.runSingleTest(testDirectory, "1", Main::main);
    }

    @Test
    public void case2() throws Exception {
        TestFileHelper.runSingleTest(testDirectory, "2", Main::main);
    }

}
