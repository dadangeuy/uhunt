package dev.rizaldi.uhunt.c3.p151;

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
    public void morass() throws Exception {
        TestHelper.run(Main::main, directory, "morass");
    }

    @Test
    @Timeout(3)
    public void kishor_aiub() throws Exception {
        TestHelper.run(Main::main, directory, "kishor_aiub");
    }

    @Test
    @Timeout(3)
    public void eduardo_figueiredo() throws Exception {
        TestHelper.run(Main::main, directory, "eduardo_figueiredo");
    }
}
