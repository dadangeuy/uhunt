package uva.uhunt.c2.g2.p12662;

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
    public void alberto_verdejo_1() throws Exception {
        TestHelper.run(Main::main, "alberto_verdejo_1");
    }

    @Test
    @Timeout(1)
    public void alberto_verdejo_2() throws Exception {
        TestHelper.run(Main::main, "alberto_verdejo_2");
    }

    @Test
    @Timeout(1)
    public void goodeath_1() throws Exception {
        TestHelper.run(Main::main, "goodeath_1");
    }

    @Test
    @Timeout(1)
    public void goodeath_2() throws Exception {
        TestHelper.run(Main::main, "goodeath_2");
    }
}
