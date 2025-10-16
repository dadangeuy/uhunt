package uhunt.c2.p939;

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
    public void udebug() throws Exception {
        TestHelper.run(Main::main, "udebug");
    }

    @Test
    @Timeout(3)
    public void robbinb1993() throws Exception {
        TestHelper.run(Main::main, "robbinb1993");
    }

    @Test
    @Timeout(3)
    public void randyw_1() throws Exception {
        TestHelper.run(Main::main, "randyw_1");
    }

    @Test
    @Timeout(3)
    public void randyw_2() throws Exception {
        TestHelper.run(Main::main, "randyw_2");
    }
}
