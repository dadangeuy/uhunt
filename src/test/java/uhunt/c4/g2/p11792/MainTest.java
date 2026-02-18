package uhunt.c4.g2.p11792;

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
    public void anjupiter() throws Exception {
        TestHelper.run(TIMEOUT, Main::main, "anjupiter");
    }

    @Test
    public void batman() throws Exception {
        TestHelper.run(TIMEOUT, Main::main, "batman");
    }
}
