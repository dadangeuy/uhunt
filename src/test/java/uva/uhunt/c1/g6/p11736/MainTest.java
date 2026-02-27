package uva.uhunt.c1.g6.p11736;

import uva.common.helper.TestHelper;
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
    public void s6088() throws Exception {
        TestHelper.run(Main::main, "s6088");
    }

    @Test
    @Timeout(1)
    public void zex() throws Exception {
        TestHelper.run(Main::main, "zex");
    }
}
