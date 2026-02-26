package uva.c7.g3.p10263;

import org.junit.jupiter.api.Test;
import uva.helper.TestHelper;

import java.time.Duration;

public class MainTest {
    private static final Duration timeout = Duration.ofSeconds(3);

    @Test
    public void sample() throws Exception {
        TestHelper.run(Main::main, "sample");
    }

    @Test
    public void uva() throws Exception {
        TestHelper.run(Main::main, "uva");
    }
}
