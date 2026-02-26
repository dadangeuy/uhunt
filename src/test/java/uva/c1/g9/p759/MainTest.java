package uva.c1.g9.p759;

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
    public void morass() throws Exception {
        TestHelper.run(Main::main, "morass");
    }

    @Test
    @Timeout(3)
    public void uva() throws Exception {
        TestHelper.run(Main::main, "uva");
    }

    @Test
    @Timeout(3)
    public void zex_1() throws Exception {
        TestHelper.run(Main::main, "zex_1");
    }

    @Test
    @Timeout(3)
    public void zex_2() throws Exception {
        TestHelper.run(Main::main, "zex_2");
    }
}
