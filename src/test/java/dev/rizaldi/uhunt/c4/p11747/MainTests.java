package dev.rizaldi.uhunt.c4.p11747;

import dev.rizaldi.uhunt.helper.TestHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.io.File;

public class MainTests {
    private final File directory = TestHelper.getDirectory(getClass());

    @Test
    @Timeout(3)
    public void sample() throws Exception {
        TestHelper.run(Main::main, directory, "sample");
    }

    @Test
    @Timeout(3)
    public void dibery() throws Exception {
        TestHelper.run(Main::main, directory, "dibery");
    }

    @Test
    @Timeout(3)
    public void anonymous() throws Exception {
        TestHelper.run(Main::main, directory, "anonymous");
    }

    @Test
    @Timeout(3)
    public void ansisg() throws Exception {
        TestHelper.run(Main::main, directory, "ansisg");
    }
}
