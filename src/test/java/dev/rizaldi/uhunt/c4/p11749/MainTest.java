package dev.rizaldi.uhunt.c4.p11749;

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
    public void alberto() throws Exception {
        TestHelper.run(Main::main, directory, "alberto");
    }

    @Test
    @Timeout(3)
    public void anjupiter() throws Exception {
        TestHelper.run(Main::main, directory, "anjupiter");
    }

    @Test
    @Timeout(3)
    public void demislam() throws Exception {
        TestHelper.run(Main::main, directory, "demislam");
    }

    @Test
    @Timeout(3)
    public void davizin() throws Exception {
        TestHelper.run(Main::main, directory, "davizin1", "davizin2", "davizin3");
    }

    @Test
    @Timeout(3)
    public void rizaldi() throws Exception {
        TestHelper.run(Main::main, directory, "rizaldi");
    }
}
