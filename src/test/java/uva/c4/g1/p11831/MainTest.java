package uva.c4.g1.p11831;

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
    public void feodorv() throws Exception {
        TestHelper.run(Main::main, "feodorv");
    }

    @Test
    @Timeout(1)
    public void morass() throws Exception {
        TestHelper.run(Main::main, "morass");
    }
}
