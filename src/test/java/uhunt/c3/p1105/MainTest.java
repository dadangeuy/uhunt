package uhunt.c3.p1105;

import org.junit.jupiter.api.Test;
import uhunt.helper.TestHelper;

import java.time.Duration;

public class MainTest {
    private static final Duration TIMEOUT = Duration.ofSeconds(5);

    @Test
    public void sample() throws Exception {
        TestHelper.run(TIMEOUT, Main::main, "sample");
    }

    @Test
    public void taraprasad() throws Exception {
        TestHelper.run(TIMEOUT, Main::main, "taraprasad");
    }

    @Test
    public void uva() throws Exception {
        TestHelper.run(TIMEOUT, Main::main, "uva");
    }
}
