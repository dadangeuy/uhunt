package uva.c2.g1.p11111;

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
    public void alberto_verdejo() throws Exception {
        TestHelper.run(Main::main, "alberto_verdejo");
    }

    @Test
    @Timeout(3)
    public void ap_222() throws Exception {
        TestHelper.run(Main::main, "ap_222");
    }

    @Test
    @Timeout(3)
    public void daniel13() throws Exception {
        TestHelper.run(Main::main, "daniel13");
    }

    @Test
    @Timeout(3)
    public void uva() throws Exception {
        TestHelper.run(Main::main, "uva");
    }
}
