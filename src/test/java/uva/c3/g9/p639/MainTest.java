package uva.c3.g9.p639;

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
    public void dread_pirate() throws Exception {
        TestHelper.run(Main::main, "dread_pirate");
    }

    @Test
    @Timeout(3)
    public void shameem_alam() throws Exception {
        TestHelper.run(Main::main, "shameem_alam");
    }

    @Test
    @Timeout(3)
    public void uva() throws Exception {
        TestHelper.run(Main::main, "uva");
    }
}
