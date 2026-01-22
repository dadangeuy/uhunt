package uhunt.c3.g0.p11520;

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
    public void morass() throws Exception {
        TestHelper.run(TIMEOUT, Main::main, "morass");
    }

    @Test
    public void brianfry() throws Exception {
        TestHelper.run(TIMEOUT, Main::main, "brianfry");
    }

    @Test
    public void rumman() throws Exception {
        TestHelper.run(TIMEOUT, Main::main, "rumman");
    }
}
