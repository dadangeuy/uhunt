package uhunt.c2.g1.p101;

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
    public void brianfry() throws Exception {
        TestHelper.run(Main::main, "brianfry");
    }

    @Test
    @Timeout(3)
    public void funcoding() throws Exception {
        TestHelper.run(Main::main, "funcoding");
    }

    @Test
    @Timeout(3)
    public void morris() throws Exception {
        TestHelper.run(Main::main, "morris");
    }
}
