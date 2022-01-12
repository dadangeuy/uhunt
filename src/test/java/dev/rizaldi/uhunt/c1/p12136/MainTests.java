package dev.rizaldi.uhunt.c1.p12136;

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
    public void morass() throws Exception {
        TestHelper.run(Main::main, directory, "morass");
    }

    @Test
    @Timeout(3)
    public void shashank21j() throws Exception {
        TestHelper.run(Main::main, directory, "shashank21j");
    }

    @Test
    @Timeout(3)
    public void udebug() throws Exception {
        TestHelper.run(Main::main, directory, "udebug");
    }

    @Test
    @Timeout(3)
    public void caffeines() throws Exception {
        TestHelper.run(Main::main, directory, "caffeines");
    }
}
