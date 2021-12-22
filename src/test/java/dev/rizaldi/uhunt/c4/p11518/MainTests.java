package dev.rizaldi.uhunt.c4.p11518;

import dev.rizaldi.uhunt.helper.TestFileHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.io.File;

public class MainTests {
    private final File directory = TestFileHelper.getTestDirectory(getClass());

    @Test
    @Timeout(2)
    public void sample() throws Exception {
        TestFileHelper.runSingleTest(directory, "sample", Main::main);
    }

    @Test
    @Timeout(2)
    public void brianfry713() throws Exception {
        TestFileHelper.runSingleTest(directory, "brianfry713", Main::main);
    }

    @Test
    @Timeout(2)
    public void faisal() throws Exception {
        TestFileHelper.runSingleTest(directory, "faisal", Main::main);
    }
}
