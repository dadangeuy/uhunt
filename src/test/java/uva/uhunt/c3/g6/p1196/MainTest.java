package uva.uhunt.c3.g6.p1196;

import org.junit.jupiter.api.Test;
import uva.common.helper.TestHelper;

import java.time.Duration;

public class MainTest {
    private final Duration TIMEOUT = Duration.ofSeconds(60);

    @Test
    public void sample() throws Exception {
        TestHelper.run(TIMEOUT, Main::main, "sample");
    }

    @Test
    public void batman() throws Exception {
        TestHelper.run(TIMEOUT, Main::main, "batman");
    }

    @Test
    public void arash() throws Exception {
        TestHelper.run(TIMEOUT, Main::main, "arash");
    }
}
