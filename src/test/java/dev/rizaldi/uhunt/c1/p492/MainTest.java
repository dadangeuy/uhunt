package dev.rizaldi.uhunt.c1.p492;

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
    public void shawon10() throws Exception {
        TestHelper.run(Main::main, directory, "shawon10");
    }

    @Test
    @Timeout(3)
    public void sojolewu() throws Exception {
        TestHelper.run(Main::main, directory, "sojolewu");
    }

    @Test
    @Timeout(3)
    public void akib_iiuc_1() throws Exception {
        TestHelper.run(Main::main, directory, "akib_iiuc_1");
    }

    @Test
    @Timeout(3)
    public void akib_iiuc_2() throws Exception {
        TestHelper.run(Main::main, directory, "akib_iiuc_2");
    }

    @Test
    @Timeout(3)
    public void akib_iiuc_3() throws Exception {
        TestHelper.run(Main::main, directory, "akib_iiuc_3");
    }

    @Test
    @Timeout(3)
    public void eduardo_figueiredo() throws Exception {
        TestHelper.run(Main::main, directory, "eduardo_figueiredo");
    }
}
