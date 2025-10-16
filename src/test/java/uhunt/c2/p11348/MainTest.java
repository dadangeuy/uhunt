package uhunt.c2.p11348;

import uhunt.helper.TestHelper;
import org.junit.jupiter.api.Test;

public class MainTest {


    @Test
    public void sample() throws Exception {
        TestHelper.run(Main::main, "sample");
    }

    @Test
    public void udebug() throws Exception {
        TestHelper.run(Main::main, "udebug");
    }

    @Test
    public void pedropablo() throws Exception {
        TestHelper.run(Main::main, "pedropablo");
    }
}
