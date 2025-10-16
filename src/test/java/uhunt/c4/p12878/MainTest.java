package uhunt.c4.p12878;

import uhunt.helper.TestHelper;
import org.junit.jupiter.api.Test;

public class MainTest {
    

    @Test
    public void sample() throws Exception {
        TestHelper.run(Main::main, "sample");
    }

    @Test
    public void alberto_verdejo_1() throws Exception {
        TestHelper.run(Main::main, "alberto.verdejo.1");
    }

    @Test
    public void alberto_verdejo_2() throws Exception {
        TestHelper.run(Main::main, "alberto.verdejo.2");
    }

    @Test
    public void alberto_verdejo_3() throws Exception {
        TestHelper.run(Main::main, "alberto.verdejo.3");
    }
}
