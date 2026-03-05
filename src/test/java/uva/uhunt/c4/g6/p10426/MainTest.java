package uva.uhunt.c4.g6.p10426;

import org.junit.jupiter.api.Test;
import uva.common.helper.TestHelper;

import java.time.Duration;

public class MainTest {
    private static final Duration TIMEOUT = Duration.ofSeconds(3);

    @Test
    public void sample() throws Exception {
        TestHelper.run(TIMEOUT, Main::main, "sample");
    }

    @Test
    public void uva() throws Exception {
        TestHelper.run(TIMEOUT, Main::main, "uva");
    }

    @Test
    public void dteo_1() throws Exception {
        TestHelper.run(TIMEOUT, Main::main, "dteo_1");
    }

    @Test
    public void dteo_2() throws Exception {
        TestHelper.run(TIMEOUT, Main::main, "dteo_2");
    }
}
