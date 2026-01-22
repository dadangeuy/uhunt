package uhunt.c3.g4.p11254;

import org.junit.jupiter.api.Test;
import uhunt.helper.TestHelper;

public class MainTest {
    @Test
    public void sample() throws Exception {
        TestHelper.run(Main::main, "sample");
    }

    @Test
    public void uva() throws Exception {
        TestHelper.run(Main::main, "uva");
    }

    @Test
    public void feodorv() throws Exception {
        TestHelper.run(Main::main, "feodorv_1", "feodorv_2");
    }
}
