package uva.uhunt.c4.g0.p11080;

import uva.common.helper.TestHelper;
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
    public void brianfry() throws Exception {
        TestHelper.run(Main::main, "brianfry");
    }

    @Test
    @Timeout(3)
    public void ahmad() throws Exception {
        TestHelper.run(Main::main, "ahmad");
    }

    @Test
    @Timeout(3)
    public void ampere() throws Exception {
        TestHelper.run(Main::main, "ampere");
    }
}
