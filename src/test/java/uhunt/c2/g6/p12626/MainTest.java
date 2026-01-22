package uhunt.c2.g6.p12626;

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
    public void shams() throws Exception {
        TestHelper.run(Main::main, "shams");
    }

    @Test
    @Timeout(1)
    public void nanda() throws Exception {
        TestHelper.run(Main::main, "nanda");
    }

    @Test
    @Timeout(1)
    public void ak94() throws Exception {
        TestHelper.run(Main::main, "ak94");
    }
}
