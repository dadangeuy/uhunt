package uhunt.c2.p10909;

import uhunt.helper.TestHelper;
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
        TestHelper.run(Main::main, "feodorv_1", "feodorv_2");
    }

    @Test
    @Timeout(3)
    public void executioner() throws Exception {
        TestHelper.run(Main::main, "executioner");
    }
}
