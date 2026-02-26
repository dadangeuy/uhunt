package uva.c4.g6.p12376;

import org.junit.jupiter.api.Test;
import uva.helper.TestHelper;

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
    public void jehad() throws Exception {
        TestHelper.run(TIMEOUT, Main::main, "jehad");
    }

    @Test
    public void milon() throws Exception {
        TestHelper.run(TIMEOUT, Main::main, "milon");
    }
}
