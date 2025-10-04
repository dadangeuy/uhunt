package dev.rizaldi.uhunt.c4.p452;

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
    public void brianfry() throws Exception {
        TestHelper.run(Main::main, directory, "brianfry");
    }

    @Test
    @Timeout(3)
    public void morass() throws Exception {
        TestHelper.run(Main::main, directory, "morass");
    }

    @Test
    @Timeout(3)
    public void mutska() throws Exception {
        TestHelper.run(Main::main, directory, "mutska");
    }

    @Test
    @Timeout(3)
    public void snowycoder() throws Exception {
        TestHelper.run(Main::main, directory, "snowycoder");
    }

    @Test
    @Timeout(3)
    public void talha() throws Exception {
        TestHelper.run(Main::main, directory, "talha");
    }
}
