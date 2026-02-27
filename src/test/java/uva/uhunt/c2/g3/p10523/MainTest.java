package uva.uhunt.c2.g3.p10523;

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
    public void udebug() throws Exception {
        TestHelper.run(Main::main, "udebug");
    }

    @Test
    @Timeout(3)
    public void _8ampere() throws Exception {
        TestHelper.run(Main::main, "_8ampere");
    }
}
