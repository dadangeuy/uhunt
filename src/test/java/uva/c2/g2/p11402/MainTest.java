package uva.c2.g2.p11402;

import uva.helper.TestHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

public class MainTest {


    @Test
    @Timeout(5)
    public void sample() throws Exception {
        TestHelper.run(Main::main, "sample");
    }

    @Test
    @Timeout(5)
    public void brianfry713() throws Exception {
        TestHelper.run(Main::main, "brianfry713");
    }

    @Test
    @Timeout(5)
    public void morass() throws Exception {
        TestHelper.run(Main::main, "morass");
    }

    @Test
    @Timeout(5)
    public void robbinb1993() throws Exception {
        TestHelper.run(Main::main, "robbinb1993");
    }

    @Test
    @Timeout(5)
    public void rrrrrrrr() throws Exception {
        TestHelper.run(Main::main, "rrrrrrrr");
    }
}
