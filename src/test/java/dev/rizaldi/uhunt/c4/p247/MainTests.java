package dev.rizaldi.uhunt.c4.p247;

import dev.rizaldi.uhunt.helper.TestFileHelper;
import org.junit.jupiter.api.Test;

import java.io.File;

public class MainTests {
    private final File testDirectory = TestFileHelper.getTestDirectory(getClass());

    @Test
    public void sample() throws Exception {
        TestFileHelper.runSingleTest(testDirectory, "sample", Main::main);
    }

    @Test
    public void rizaldi() throws Exception {
        TestFileHelper.runSingleTest(testDirectory, "rizaldi", Main::main);
    }
}
