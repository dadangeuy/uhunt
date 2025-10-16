package uhunt.c1.p11678;

import uhunt.helper.TestHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

public class MainTest {
    

    @Test
    @Timeout(1)
    public void sample() throws Exception {
        TestHelper.run(Main::main, "sample");
    }

    @Test
    @Timeout(1)
    public void udebug_1() throws Exception {
        TestHelper.run(Main::main, "udebug_1");
    }

    @Test
    @Timeout(1)
    public void udebug_2() throws Exception {
        TestHelper.run(Main::main, "udebug_2");
    }
}
