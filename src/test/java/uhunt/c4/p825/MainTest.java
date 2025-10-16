package uhunt.c4.p825;

import uhunt.helper.TestHelper;
import org.junit.jupiter.api.Test;

public class MainTest {


    @Test
    public void sample() throws Exception {
        TestHelper.run(Main::main, "sample");
    }

    @Test
    public void uva() throws Exception {
        TestHelper.run(Main::main, "uva");
    }

    @Test
    public void amiratou() throws Exception {
        TestHelper.run(Main::main, "amiratou");
    }

    @Test
    public void alamkhan() throws Exception {
        TestHelper.run(Main::main, "alamkhan");
    }
}
