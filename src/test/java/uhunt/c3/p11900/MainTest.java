package uhunt.c3.p11900;

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
    public void udebug() throws Exception {
        TestHelper.run(Main::main, "udebug");
    }

    @Test
    @Timeout(1)
    public void debugster() throws Exception {
        TestHelper.run(Main::main, "debugster");
    }

    @Test
    @Timeout(1)
    public void plabon022() throws Exception {
        TestHelper.run(Main::main, "plabon022");
    }

    @Test
    @Timeout(1)
    public void ak94() throws Exception {
        TestHelper.run(Main::main, "ak94");
    }
}
