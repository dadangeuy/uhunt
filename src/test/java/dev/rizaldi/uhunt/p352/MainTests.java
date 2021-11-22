package dev.rizaldi.uhunt.p352;

import dev.rizaldi.uhunt.helper.TestFileHelper;
import org.junit.jupiter.api.Test;

import java.io.File;

public class MainTests {
    private final File testDirectory = TestFileHelper.getTestDirectory(getClass());

    @Test
    public void case1() throws Exception {
        TestFileHelper.runSingleTest(testDirectory, "1", Main::main);
    }

    @Test
    public void case2() throws Exception {
        TestFileHelper.runSingleTest(testDirectory, "2", Main::main);
    }
}
