package uhunt.c4.p1056;

import uhunt.helper.TestHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

public class MainTest {
    private static final int TIMEOUT = 3;
    

    @Test
    @Timeout(TIMEOUT)
    public void sample() throws Exception {
        TestHelper.run(Main::main, "sample");
    }

    @Test
    @Timeout(TIMEOUT)
    public void anjupiter() throws Exception {
        TestHelper.run(Main::main, "anjupiter");
    }

    @Test
    @Timeout(TIMEOUT)
    public void batman() throws Exception {
        TestHelper.run(Main::main, "batman");
    }
}
