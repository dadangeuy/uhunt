package uhunt.c2.g1.p12291;

import uhunt.helper.TestHelper;
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
    public void morass_1() throws Exception {
        TestHelper.run(Main::main, "morass_1");
    }

    @Test
    @Timeout(1)
    public void morass_2() throws Exception {
        TestHelper.run(Main::main, "morass_2");
    }


    @Test
    @Timeout(1)
    public void robbinb1993() throws Exception {
        TestHelper.run(Main::main, "robbinb1993");
    }


    @Test
    @Timeout(1)
    public void cweb_cpp() throws Exception {
        TestHelper.run(Main::main, "cweb_cpp");
    }
}
