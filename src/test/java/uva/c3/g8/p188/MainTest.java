package uva.c3.g8.p188;

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
    public void feodorv() throws Exception {
        TestHelper.run(TIMEOUT, Main::main, "feodorv");
    }
}
