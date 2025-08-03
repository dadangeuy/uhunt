package dev.rizaldi.uhunt.c2.p101;

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
    public void funcoding() throws Exception {
        TestHelper.run(Main::main, directory, "funcoding");
    }

    @Test
    @Timeout(3)
    public void morris() throws Exception {
        TestHelper.run(Main::main, directory, "morris");
    }
}
