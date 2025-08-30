package dev.rizaldi.uhunt.c4.p11585;

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
    public void uva() throws Exception {
        TestHelper.run(Main::main, directory, "uva");
    }

    @Test
    @Timeout(1)
    public void outsbook() throws Exception {
        TestHelper.run(Main::main, directory, "outsbook");
    }
}
