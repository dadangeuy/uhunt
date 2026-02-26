package uva.c7.g3.p11783;

import org.junit.jupiter.api.Test;
import uva.helper.TestHelper;

import java.time.Duration;

public class MainTest {
    private static Duration TIMEOUT = Duration.ofSeconds(1);

    @Test
    public void sample() throws Exception {
        TestHelper.run(TIMEOUT, Main::main, "sample");
    }

    @Test
    public void metaphysis() throws Exception {
        TestHelper.run(TIMEOUT, Main::main, "metaphysis");
    }
}
