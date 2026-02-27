package uva.uhunt.c1.g9.p11279;

import uva.common.helper.TestHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

public class MainTest {
    

    @Test
    @Timeout(2)
    public void sample() throws Exception {
        TestHelper.run(Main::main, "sample");
    }

    @Test
    @Timeout(2)
    public void bryton() throws Exception {
        TestHelper.run(Main::main, "bryton");
    }

    @Test
    @Timeout(2)
    public void zex() throws Exception {
        TestHelper.run(Main::main, "zex");
    }
}
