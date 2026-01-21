package uhunt.c7.p1595;

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
    public void feodorv_1() throws Exception {
        TestHelper.run(TIMEOUT, Main::main, "feodorv_1");
    }

    @Test
    public void feodorv_2() throws Exception {
        TestHelper.run(TIMEOUT, Main::main, "feodorv_2");
    }
}
