package uva.c2.g2.p13212;

import uva.helper.TestHelper;
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
    public void morass_1() throws Exception {
        TestHelper.run(Main::main, "morass_1");
    }

    @Test
    @Timeout(1)
    public void morass_2() throws Exception {
        TestHelper.run(Main::main, "morass_2");
    }

    @Test
    @Timeout(1)
    public void morass_3() throws Exception {
        TestHelper.run(Main::main, "morass_3");
    }
}
