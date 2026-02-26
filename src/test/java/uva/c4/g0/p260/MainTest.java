package uva.c4.g0.p260;

import uva.helper.TestHelper;
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
    public void brianfry() throws Exception {
        TestHelper.run(Main::main, "brianfry");
    }

    @Test
    @Timeout(3)
    public void twyu() throws Exception {
        TestHelper.run(Main::main, "twyu");
    }

    @Test
    @Timeout(3)
    public void nakar() throws Exception {
        TestHelper.run(Main::main, "nakar");
    }
}
