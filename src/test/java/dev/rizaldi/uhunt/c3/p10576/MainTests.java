package dev.rizaldi.uhunt.c3.p10576;

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
    public void brianfry713() throws Exception {
        TestHelper.run(Main::main, directory, "brianfry713");
    }

    @Test
    @Timeout(3)
    public void outsbook() throws Exception {
        TestHelper.run(Main::main, directory, "outsbook");
    }
}