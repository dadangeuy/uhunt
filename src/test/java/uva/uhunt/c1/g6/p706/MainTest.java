package uva.uhunt.c1.g6.p706;

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
    public void uva() throws Exception {
        TestHelper.run(Main::main, "uva");
    }

    @Test
    @Timeout(3)
    public void nakar81() throws Exception {
        TestHelper.run(Main::main, "nakar81");
    }

    @Test
    @Timeout(3)
    public void r19() throws Exception {
        TestHelper.run(Main::main, "r19");
    }
}
