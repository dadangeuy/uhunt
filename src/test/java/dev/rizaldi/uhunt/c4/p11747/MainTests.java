package dev.rizaldi.uhunt.c4.p11747;

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
    public void dibery() throws Exception {
        TestFileHelper.runSingleTest(directory, "dibery", Main::main);
    }

    @Test
    @Timeout(3)
    public void anonymous() throws Exception {
        TestFileHelper.runSingleTest(directory, "anonymous", Main::main);
    }

    @Test
    @Timeout(3)
    public void ansisg() throws Exception {
        TestFileHelper.runSingleTest(directory, "ansisg", Main::main);
    }
}
