package uhunt.c4.p872;

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
    public void fabikw() throws Exception {
        TestHelper.run(Main::main, "fabikw");
    }

    @Test
    @Timeout(3)
    public void anonymous() throws Exception {
        TestHelper.run(Main::main, "anonymous");
    }

    @Test
    @Timeout(3)
    public void twyu() throws Exception {
        TestHelper.run(Main::main, "twyu");
    }
}
