package uhunt.c2.p11550;

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
    public void udebug1() throws Exception {
        TestHelper.run(Main::main, "udebug_1");
    }

    @Test
    @Timeout(1)
    public void udebug2() throws Exception {
        TestHelper.run(Main::main, "udebug_2");
    }
}
