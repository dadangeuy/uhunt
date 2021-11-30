package dev.rizaldi.uhunt.c4.p200;

import dev.rizaldi.uhunt.helper.TestFileHelper;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.URISyntaxException;

public class MainTests {
    private final File testDirectory = TestFileHelper.getTestDirectory(getClass());

    public MainTests() throws URISyntaxException {
    }

    @Test
    public void example1() throws Exception {
        TestFileHelper.runSingleTest(testDirectory, "1", Main::main);
    }

    @Test
    public void example2() throws Exception {
        TestFileHelper.runSingleTest(testDirectory, "2", Main::main);
    }

    @Test
    public void example3() throws Exception {
        TestFileHelper.runSingleTest(testDirectory, "3", Main::main);
    }
}
