package uva.uhunt.c4.g2.p11352;

import org.junit.jupiter.api.Test;
import uva.common.helper.TestHelper;

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
    public void davizin() throws Exception {
        TestHelper.run(Main::main, "davizin");
    }
}
