package uva.uhunt.c2.g0.p11300;

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
    public void pedropablo() throws Exception {
        TestHelper.run(Main::main, "pedropablo");
    }

    @Test
    @Timeout(3)
    public void rizaldi() throws Exception {
        TestHelper.run(Main::main, "rizaldi");
    }
}
