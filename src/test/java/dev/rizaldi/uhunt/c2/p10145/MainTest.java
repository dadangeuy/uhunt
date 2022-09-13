package dev.rizaldi.uhunt.c2.p10145;

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
    public void goodeath_1() throws Exception {
        TestHelper.run(Main::main, directory, "goodeath_1");
    }

    @Test
    @Timeout(3)
    public void goodeath_2() throws Exception {
        TestHelper.run(Main::main, directory, "goodeath_2");
    }

    @Test
    @Timeout(3)
    public void ikadery() throws Exception {
        TestHelper.run(Main::main, directory, "ikadery");
    }

    @Test
    @Timeout(3)
    public void dibery() throws Exception {
        TestHelper.run(Main::main, directory, "dibery");
    }

    @Test
    @Timeout(3)
    public void kac3pro() throws Exception {
        TestHelper.run(Main::main, directory, "kac3pro");
    }
}
