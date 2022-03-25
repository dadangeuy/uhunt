package dev.rizaldi.uhunt.c3.p11517;

import dev.rizaldi.uhunt.helper.TestHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.io.File;

public class MainTest {
    private final File directory = TestHelper.getDirectory(getClass());

    @Test
    @Timeout(1)
    public void sample() throws Exception {
        TestHelper.run(Main::main, directory, "sample");
    }

    @Test
    @Timeout(1)
    public void brianfry713() throws Exception {
        TestHelper.run(Main::main, directory, "brianfry713");
    }

    @Test
    @Timeout(1)
    public void rofi93_1() throws Exception {
        TestHelper.run(Main::main, directory, "rofi93_1");
    }

    @Test
    @Timeout(1)
    public void rofi93_2() throws Exception {
        TestHelper.run(Main::main, directory, "rofi93_2");
    }

    @Test
    @Timeout(1)
    public void nasher() throws Exception {
        TestHelper.run(Main::main, directory, "nasher");
    }

    @Test
    @Timeout(1)
    public void rizaldi() throws Exception {
        TestHelper.run(Main::main, directory, "rizaldi");
    }
}
