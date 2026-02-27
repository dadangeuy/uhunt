package uva.uhunt.c3.g1.p11491;

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
    public void uva() throws Exception {
        TestHelper.run(Main::main, "uva");
    }

    @Test
    @Timeout(1)
    public void alberto() throws Exception {
        TestHelper.run(Main::main, "alberto_1", "alberto_2");
    }
}
