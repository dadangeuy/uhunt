package uva.uhunt.c1.g1.p1061;

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
    public void feodorv() throws Exception {
        TestHelper.run(Main::main, "feodorv");
    }

    @Test
    @Timeout(3)
    public void antonyd() throws Exception {
        TestHelper.run(Main::main, "antonyd");
    }

    @Test
    @Timeout(3)
    public void uva() throws Exception {
        TestHelper.run(Main::main, "uva");
    }

    @Test
    @Timeout(3)
    public void ebb() throws Exception {
        TestHelper.run(Main::main, "ebb");
    }
}
