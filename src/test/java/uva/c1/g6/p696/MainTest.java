package uva.c1.g6.p696;

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
    public void zapstar() throws Exception {
        TestHelper.run(Main::main, "zapstar");
    }

    @Test
    @Timeout(3)
    public void feodorv() throws Exception {
        TestHelper.run(Main::main, "feodorv");
    }
}
