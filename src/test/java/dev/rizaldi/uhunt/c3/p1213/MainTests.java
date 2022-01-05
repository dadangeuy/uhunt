package dev.rizaldi.uhunt.c3.p1213;

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
    public void anjupiter() throws Exception {
        TestHelper.run(Main::main, directory, "anjupiter");
    }

    @Test
    @Timeout(3)
    public void aunkon() throws Exception {
        TestHelper.run(Main::main, directory, "aunkon");
    }

    @Test
    @Timeout(3)
    public void bartleby() throws Exception {
        TestHelper.run(Main::main, directory, "bartleby");
    }
}
