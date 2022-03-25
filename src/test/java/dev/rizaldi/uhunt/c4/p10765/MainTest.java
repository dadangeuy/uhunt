package dev.rizaldi.uhunt.c4.p10765;

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
    public void anjupiter() throws Exception {
        TestHelper.run(Main::main, directory, "anjupiter");
    }

    @Test
    @Timeout(3)
    public void fer22f_1() throws Exception {
        TestHelper.run(Main::main, directory, "fer22f_1");
    }

    @Test
    @Timeout(3)
    public void fer22f_2() throws Exception {
        TestHelper.run(Main::main, directory, "fer22f_2");
    }

    @Test
    public void rizaldi() throws Exception {
        TestHelper.run(Main::main, directory, "rizaldi");
    }
}
