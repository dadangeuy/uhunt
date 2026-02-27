package uva.uhunt.c4.g1.p11631;

import uva.common.helper.TestHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

public class MainTest {
    

    @Test
    @Timeout(2)
    public void sample() throws Exception {
        TestHelper.run(Main::main, "sample");
    }

    @Test
    @Timeout(2)
    public void batman() throws Exception {
        TestHelper.run(Main::main, "batman");
    }

    @Test
    @Timeout(2)
    public void uva() throws Exception {
        TestHelper.run(Main::main, "uva");
    }
}
