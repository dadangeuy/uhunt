package uhunt.c2.g6.p466;

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
    public void cyktsui() throws Exception {
        TestHelper.run(Main::main, "cyktsui");
    }

    @Test
    @Timeout(3)
    public void feodorv_1() throws Exception {
        TestHelper.run(Main::main, "feodorv_1");
    }

    @Test
    @Timeout(3)
    public void feodorv_2() throws Exception {
        TestHelper.run(Main::main, "feodorv_2");
    }

    @Test
    @Timeout(3)
    public void feodorv_3() throws Exception {
        TestHelper.run(Main::main, "feodorv_3");
    }
}
