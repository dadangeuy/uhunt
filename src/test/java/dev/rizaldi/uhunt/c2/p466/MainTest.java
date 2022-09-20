package dev.rizaldi.uhunt.c2.p466;

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
    public void cyktsui() throws Exception {
        TestHelper.run(Main::main, directory, "cyktsui");
    }

    @Test
    @Timeout(3)
    public void feodorv_1() throws Exception {
        TestHelper.run(Main::main, directory, "feodorv_1");
    }

    @Test
    @Timeout(3)
    public void feodorv_2() throws Exception {
        TestHelper.run(Main::main, directory, "feodorv_2");
    }

    @Test
    @Timeout(3)
    public void feodorv_3() throws Exception {
        TestHelper.run(Main::main, directory, "feodorv_3");
    }
}
