package uva.c2.g2.p11062;

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
    public void uva() throws Exception {
        TestHelper.run(Main::main, "uva");
    }

    @Test
    @Timeout(3)
    public void amr() throws Exception {
        TestHelper.run(Main::main, "amr");
    }

    @Test
    @Timeout(3)
    public void rohi() throws Exception {
        TestHelper.run(Main::main, "rohi");
    }
}
