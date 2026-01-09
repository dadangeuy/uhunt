package uhunt.c3.p1153;

import org.junit.jupiter.api.Test;
import uhunt.helper.TestHelper;

import java.time.Duration;

public class MainTest {
    private static final Duration TIMEOUT = Duration.ofSeconds(3);

    @Test
    public void sample() throws Exception {
        TestHelper.run(TIMEOUT, Main::main, "sample");
    }

    @Test
    public void ashik() throws Exception {
        TestHelper.run(TIMEOUT, Main::main, "ashik");
    }

    @Test
    public void r() throws Exception {
        TestHelper.run(TIMEOUT, Main::main, "r");
    }
}
