package uhunt.c3.g8.p10448;

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
    public void starswap() throws Exception {
        TestHelper.run(Main::main, "starswap_1", "starswap_2");
    }

    @Test
    @Timeout(3)
    public void uva() throws Exception {
        TestHelper.run(Main::main, "uva");
    }

    @Test
    @Timeout(3)
    public void razumnik() throws Exception {
        TestHelper.run(Main::main, "razumnik");
    }
}
