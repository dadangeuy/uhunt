package uva.c3.g2.p242;

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
    public void calrare() throws Exception {
        TestHelper.run(Main::main, "calrare");
    }

    @Test
    @Timeout(3)
    public void feodorv() throws Exception {
        TestHelper.run(Main::main, "feodorv");
    }

    @Test
    @Timeout(3)
    public void r() throws Exception {
        TestHelper.run(Main::main, "r_1", "r_2", "r_3");
    }
}
