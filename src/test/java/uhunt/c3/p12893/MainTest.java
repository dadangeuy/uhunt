package uhunt.c3.p12893;

import org.junit.jupiter.api.Test;
import uhunt.helper.TestHelper;

import java.time.Duration;

public class MainTest {
    private static final Duration TIMEOUT = Duration.ofSeconds(1);

    @Test
    public void sample() throws Exception {
        TestHelper.run(TIMEOUT, Main::main, "sample");
    }

    @Test
    public void feodorv() throws Exception {
        TestHelper.run(TIMEOUT, Main::main, "feodorv");
    }

    @Test
    public void morass() throws Exception {
        TestHelper.run(TIMEOUT, Main::main, "morass");
    }
}
