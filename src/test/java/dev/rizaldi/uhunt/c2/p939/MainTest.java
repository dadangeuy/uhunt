package dev.rizaldi.uhunt.c2.p939;

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
    public void udebug() throws Exception {
        TestHelper.run(Main::main, directory, "udebug");
    }

    @Test
    @Timeout(3)
    public void robbinb1993() throws Exception {
        TestHelper.run(Main::main, directory, "robbinb1993");
    }

    @Test
    @Timeout(3)
    public void randyw_1() throws Exception {
        TestHelper.run(Main::main, directory, "randyw_1");
    }

    @Test
    @Timeout(3)
    public void randyw_2() throws Exception {
        TestHelper.run(Main::main, directory, "randyw_2");
    }
}
