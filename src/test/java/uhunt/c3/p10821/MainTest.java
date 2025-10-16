package uhunt.c3.p10821;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import uhunt.helper.TestHelper;

public class MainTest {
    @Test
    @Timeout(3)
    public void sample() throws Exception {
        TestHelper.run(Main::main, "sample");
    }

    @Test
    @Timeout(3)
    public void bryton() throws Exception {
        TestHelper.run(Main::main, "bryton");
    }

    @Test
    @Timeout(3)
    public void anjupiter() throws Exception {
        TestHelper.run(Main::main, "anjupiter");
    }
}
