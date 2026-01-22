package uhunt.c3.g5.p725;

import uhunt.helper.TestHelper;
import org.junit.jupiter.api.Test;

public class MainTest {
    

    @Test
    public void sample() throws Exception {
        TestHelper.run(Main::main, "sample");
    }

    @Test
    public void udebug1() throws Exception {
        TestHelper.run(Main::main, "udebug_1");
    }

    @Test
    public void udebug2() throws Exception {
        TestHelper.run(Main::main, "udebug_2");
    }
}
