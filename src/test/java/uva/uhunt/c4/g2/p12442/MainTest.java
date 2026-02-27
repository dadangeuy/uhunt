package uva.uhunt.c4.g2.p12442;

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
    public void anjupiter() throws Exception {
        TestHelper.run(Main::main, "anjupiter");
    }

    @Test
    @Timeout(1)
    public void a7med() throws Exception {
        TestHelper.run(Main::main, "a7med");
    }
}
