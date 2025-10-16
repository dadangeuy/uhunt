package uhunt.c1.p255;

import uhunt.helper.TestHelper;
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
    public void bryton() throws Exception {
        TestHelper.run(Main::main, "bryton");
    }

    @Test
    @Timeout(3)
    public void the_madman() throws Exception {
        TestHelper.run(Main::main, "the_madman");
    }

    @Test
    @Timeout(3)
    public void lujoselu98() throws Exception {
        TestHelper.run(Main::main, "lujoselu98");
    }
}
