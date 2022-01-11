package dev.rizaldi.uhunt.c4.p11280;

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
    public void anasschoukri() throws Exception {
        TestHelper.run(Main::main, directory, "anasschoukri");
    }

    @Test
    @Timeout(3)
    public void batman() throws Exception {
        TestHelper.run(Main::main, directory, "batman");
    }

    @Test
    @Timeout(3)
    public void brianfry713() throws Exception {
        TestHelper.run(Main::main, directory, "brianfry713");
    }

    @Test
    @Timeout(3)
    public void dvrodri8() throws Exception {
        TestHelper.run(Main::main, directory, "dvrodri8");
    }

    @Test
    @Timeout(3)
    public void fatemehkarimi() throws Exception {
        TestHelper.run(Main::main, directory, "fatemehkarimi");
    }

    @Test
    public void rizaldi() throws Exception {
        TestHelper.run(Main::main, directory, "rizaldi");
    }
}
