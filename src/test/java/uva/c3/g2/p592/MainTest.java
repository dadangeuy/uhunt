package uva.c3.g2.p592;

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
    public void vw() throws Exception {
        TestHelper.run(TIMEOUT, Main::main, "vw");
    }

    @Test
    public void rizaldi() throws Exception {
        TestHelper.run(TIMEOUT, Main::main, "rizaldi");
    }
}
