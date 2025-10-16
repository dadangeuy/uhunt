package uhunt.c2.p499;

import uhunt.helper.TestHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

public class MainTest {
    

    @Test
    @Timeout(3)
    public void sample() throws Exception {
        TestHelper.run(Main::main, "sample");
    }

    @Test
    @Timeout(3)
    public void uva() throws Exception {
        TestHelper.run(Main::main, "uva");
    }

    @Test
    @Timeout(3)
    public void debugster_1() throws Exception {
        TestHelper.run(Main::main, "debugster_1");
    }

    @Test
    @Timeout(3)
    public void debugster_2() throws Exception {
        TestHelper.run(Main::main, "debugster_2");
    }
}
