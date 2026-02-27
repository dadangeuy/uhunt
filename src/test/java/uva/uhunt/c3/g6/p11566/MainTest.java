package uva.uhunt.c3.g6.p11566;

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
    public void brianfry() throws Exception {
        TestHelper.run(Main::main, "brianfry");
    }

    @Test
    @Timeout(1)
    public void lennart() throws Exception {
        TestHelper.run(Main::main, "lennart");
    }
}
