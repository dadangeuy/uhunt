package uhunt.c2.g9.p11629;

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
    public void anjupiter1() throws Exception {
        TestHelper.run(Main::main, "anjupiter_1");
    }

    @Test
    @Timeout(1)
    public void anjupiter2() throws Exception {
        TestHelper.run(Main::main, "anjupiter_2");
    }
}
