package uhunt.c2.g0.p1610;

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
    public void anacharsis_1() throws Exception {
        TestHelper.run(Main::main, "anacharsis_1");
    }

    @Test
    @Timeout(3)
    public void anacharsis_2() throws Exception {
        TestHelper.run(Main::main, "anacharsis_2");
    }

    @Test
    @Timeout(3)
    public void txomin2091_1() throws Exception {
        TestHelper.run(Main::main, "txomin2091_1");
    }

    @Test
    @Timeout(3)
    public void txomin2091_2() throws Exception {
        TestHelper.run(Main::main, "txomin2091_2");
    }
}
