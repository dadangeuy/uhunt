package uva.uhunt.c3.g0.p12390;

import uva.common.helper.TestHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

public class MainTest {
    

    @Test
    @Timeout(4)
    public void sample() throws Exception {
        TestHelper.run(Main::main, "sample");
    }

    @Test
    @Timeout(4)
    public void anjupiter() throws Exception {
        TestHelper.run(Main::main, "anjupiter");
    }

    @Test
    @Timeout(4)
    public void morass() throws Exception {
        TestHelper.run(Main::main, "morass");
    }
}
