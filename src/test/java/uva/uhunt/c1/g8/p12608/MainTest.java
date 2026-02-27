package uva.uhunt.c1.g8.p12608;

import uva.common.helper.TestHelper;
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
    public void batman() throws Exception {
        TestHelper.run(Main::main, "batman");
    }

    @Test
    @Timeout(1)
    public void zex() throws Exception {
        TestHelper.run(Main::main, "zex");
    }
}
