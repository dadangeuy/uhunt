package uhunt.c1.p10188;

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
    public void r19() throws Exception {
        TestHelper.run(Main::main, "r19");
    }

    @Test
    @Timeout(3)
    public void matheus_aguilar() throws Exception {
        TestHelper.run(Main::main, "matheus_aguilar");
    }
}
