package uva.c4.g5.p1265;

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
    public void morass() throws Exception {
        TestHelper.run(TIMEOUT, Main::main, "morass");
    }

    @Test
    public void uva() throws Exception {
        TestHelper.run(TIMEOUT, Main::main, "uva");
    }

    @Test
    public void rizaldi() throws Exception {
        TestHelper.run(TIMEOUT, Main::main, "rizaldi");
    }

    @Test
    public void rizaldi_2() throws Exception {
        TestHelper.run(TIMEOUT, Main::main, "rizaldi_2");
    }
}
