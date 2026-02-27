package uva.uhunt.c1.g6.p12896;

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
    public void monir004() throws Exception {
        TestHelper.run(Main::main, "monir004");
    }

    @Test
    @Timeout(1)
    public void morass() throws Exception {
        TestHelper.run(Main::main, "morass");
    }
}
