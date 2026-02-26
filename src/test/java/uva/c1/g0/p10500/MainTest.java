package uva.c1.g0.p10500;

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
    public void uva_1() throws Exception {
        TestHelper.run(Main::main, "uva_1");
    }

    @Test
    @Timeout(3)
    public void uva_2() throws Exception {
        TestHelper.run(Main::main, "uva_2");
    }
}
