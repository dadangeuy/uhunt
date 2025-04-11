package dev.rizaldi.uhunt.c2.p127;

import dev.rizaldi.uhunt.helper.TestHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.io.File;

public class MainTest {
    private final File directory = TestHelper.getDirectory(getClass());

    @Test
    @Timeout(3)
    public void sample() throws Exception {
        TestHelper.run(Main::main, directory, "sample");
    }

    @Test
    @Timeout(3)
    public void uva() throws Exception {
        TestHelper.run(Main::main, directory, "uva");
    }

    @Test
    @Timeout(3)
    public void ksqf() throws Exception {
        TestHelper.run(Main::main, directory, "ksqf");
    }

    @Test
    @Timeout(3)
    public void rizaldi_1() throws Exception {
        TestHelper.run(Main::main, directory, "rizaldi_1");
    }
}
