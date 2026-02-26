package uva.c4.g0.p11060;

import uva.helper.TestHelper;
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
    public void zeronfinity() throws Exception {
        TestHelper.run(Main::main, "zeronfinity");
    }
}
