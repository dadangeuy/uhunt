package dev.rizaldi.uhunt.c4.p12047;

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
    public void ashif() throws Exception {
        TestHelper.run(Main::main, directory, "ashif1", "ashif2");
    }

    @Test
//    @Timeout(3)
    public void fatemehkarimi() throws Exception {
        TestHelper.run(Main::main, directory, "fatemehkarimi");
    }

    @Test
    @Timeout(3)
    public void gautham() throws Exception {
        TestHelper.run(Main::main, directory, "gautham");
    }
}
