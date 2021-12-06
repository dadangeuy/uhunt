package dev.rizaldi.uhunt.c3.p10344;

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
    public void rizaldi() throws Exception {
        TestFileHelper.runSingleTest(directory, "rizaldi", Main::main);
    }
}
