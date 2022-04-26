package dev.rizaldi.uhunt.c1.p1605;

import dev.rizaldi.uhunt.helper.TestHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.io.File;

public class MainTest {
    private final File directory = TestHelper.getDirectory(getClass());

    @Test
    @Timeout(3)
    public void marcoa() throws Exception {
        TestHelper.run(Main::main, directory, "marcoa");
    }

    @Test
    @Timeout(3)
    public void rizaldi() throws Exception {
        TestHelper.run(Main::main, directory, "rizaldi");
    }
}
