package uhunt.c3.g2.p10912;

import org.junit.jupiter.api.Test;
import uhunt.helper.TestHelper;

public class MainTest {
    @Test
    public void sample() throws Exception {
        TestHelper.run(Main::main, "sample");
    }

    @Test
    public void anonymous() throws Exception {
        TestHelper.run(Main::main, "anonymous");
    }

    @Test
    public void shawnliang() throws Exception {
        TestHelper.run(Main::main, "shawnliang");
    }
}
