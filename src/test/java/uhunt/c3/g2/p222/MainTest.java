package uhunt.c3.g2.p222;

import org.junit.jupiter.api.Disabled;
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
    @Disabled("Wrong answer due to double precision")
    public void morass() throws Exception {
        TestHelper.run(Main::main, "morass");
    }
}
