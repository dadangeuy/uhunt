package uhunt.c7.g8.p10678;

import org.junit.jupiter.api.Test;
import uhunt.helper.TestHelper;

import java.time.Duration;

public class MainTest {
    private static final Duration timeout = Duration.ofSeconds(3);

    @Test
    public void sample() throws Exception {
        TestHelper.run(timeout, Main::main, "sample");
    }

    @Test
    public void executioner() throws Exception {
        TestHelper.run(timeout, Main::main, "executioner");
    }
}
