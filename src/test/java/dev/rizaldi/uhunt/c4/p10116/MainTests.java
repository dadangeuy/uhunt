package dev.rizaldi.uhunt.c4.p10116;

import dev.rizaldi.uhunt.helper.TestFileHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.io.File;

public class MainTests {
    private final File directory = TestFileHelper.getTestDirectory(getClass());

    @Test
    @Timeout(3)
    public void sample() throws Exception {
        TestFileHelper.runSingleTest(directory, "sample", Main::main);
    }

    @Test
    @Timeout(3)
    public void uva() throws Exception {
        TestFileHelper.runSingleTest(directory, "uva", Main::main);
    }

    @Test
    @Timeout(3)
    public void morass() throws Exception {
        TestFileHelper.runSingleTest(directory, "morass", Main::main);
    }
}
