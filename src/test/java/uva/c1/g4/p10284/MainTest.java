package uva.c1.g4.p10284;

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
    public void dibery() throws Exception {
        TestHelper.run(Main::main, "dibery");
    }

    @Test
    @Timeout(3)
    public void ekangas() throws Exception {
        TestHelper.run(Main::main, "ekangas");
    }

    @Test
    @Timeout(3)
    public void clx() throws Exception {
        TestHelper.run(Main::main, "sample");
    }
}
