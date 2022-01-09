package dev.rizaldi.uhunt.c4.p11631;

import dev.rizaldi.uhunt.helper.TestHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.io.File;

public class MainTests {
    private final File directory = TestHelper.getDirectory(getClass());

    @Test
    @Timeout(2)
    public void sample() throws Exception {
        TestHelper.run(Main::main, directory, "sample");
    }

    @Test
    @Timeout(2)
    public void batman() throws Exception {
        TestHelper.run(Main::main, directory, "batman");
    }

    @Test
    @Timeout(2)
    public void uva() throws Exception {
        TestHelper.run(Main::main, directory, "uva");
    }
}
