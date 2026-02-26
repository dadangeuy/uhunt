package uva.c1.g8.p11638;

import uva.helper.TestHelper;
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
    public void ctrlalt() throws Exception {
        TestHelper.run(Main::main, "ctrlalt");
    }

    @Test
    @Timeout(1)
    public void uva() throws Exception {
        TestHelper.run(Main::main, "uva");
    }
}
