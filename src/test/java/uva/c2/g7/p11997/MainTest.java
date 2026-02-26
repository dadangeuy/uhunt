package uva.c2.g7.p11997;

import uva.helper.TestHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

public class MainTest {
    

    @Test
    @Timeout(1)
    public void case1() throws Exception {
        TestHelper.run(Main::main, "1");
    }

    @Test
    @Timeout(1)
    public void case2() throws Exception {
        TestHelper.run(Main::main, "2");
    }

    @Test
    @Timeout(1)
    public void case3() throws Exception {
        TestHelper.run(Main::main, "3");
    }
}
