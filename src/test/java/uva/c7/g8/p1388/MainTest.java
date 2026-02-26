package uva.c7.g8.p1388;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import uva.helper.TestHelper;

import java.time.Duration;

public class MainTest {
    private static final Duration TIMEOUT = Duration.ofSeconds(3);

    @Test
    public void sample() throws Exception {
        TestHelper.run(TIMEOUT, Main::main, "sample");
    }

    @Test
    @Disabled("non-deterministic output")
    public void anacharsis() throws Exception {
        TestHelper.run(TIMEOUT, Main::main, "anacharsis");
    }
}
