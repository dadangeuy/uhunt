package uva.uhunt.c4.g5.p11695;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import uva.common.helper.TestHelper;

import java.time.Duration;

public class MainTest {
    private static final Duration TIMEOUT = Duration.ofSeconds(15);

    @Test
    @Disabled("non-deterministic output")
    public void sample() throws Exception {
        TestHelper.run(TIMEOUT, Main::main, "sample");
    }

    @Test
    @Disabled("non-deterministic output")
    public void marcoa_1() throws Exception {
        TestHelper.run(TIMEOUT, Main::main, "marcoa_1");
    }

    @Test
    @Disabled("non-deterministic output")
    public void marcoa_2() throws Exception {
        TestHelper.run(TIMEOUT, Main::main, "marcoa_2");
    }

    @Test
    @Disabled("non-deterministic output")
    public void marcoa_3() throws Exception {
        TestHelper.run(TIMEOUT, Main::main, "marcoa_3");
    }
}
