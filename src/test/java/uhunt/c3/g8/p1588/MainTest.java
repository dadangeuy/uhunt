package uhunt.c3.g8.p1588;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import uhunt.helper.TestHelper;

@Nested
@Timeout(3)
public class MainTest {
    @Test
    public void sample() throws Exception {
        TestHelper.run(Main::main, "sample");
    }

    @Test
    public void eri() throws Exception {
        TestHelper.run(Main::main, "eri");
    }

    @Test
    public void txomin() throws Exception {
        TestHelper.run(Main::main, "txomin");
    }
}
