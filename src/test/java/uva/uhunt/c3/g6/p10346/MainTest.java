package uva.uhunt.c3.g6.p10346;

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
    public void udebug() throws Exception {
        TestHelper.run(TIMEOUT, Main::main, "udebug");
    }

    @Test
    public void debugster() throws Exception {
        TestHelper.run(TIMEOUT, Main::main, "debugster");
    }

    @Test
    public void shams() throws Exception {
        TestHelper.run(TIMEOUT, Main::main, "shams");
    }

    @Test
    public void uva() throws Exception {
        TestHelper.run(TIMEOUT, Main::main, "uva");
    }
}
