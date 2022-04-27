package dev.rizaldi.uhunt.c1.p10500;

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
    public void uva_1() throws Exception {
        TestHelper.run(Main::main, directory, "uva_1");
    }

    @Test
    @Timeout(3)
    public void uva_2() throws Exception {
        TestHelper.run(Main::main, directory, "uva_2");
    }
}
