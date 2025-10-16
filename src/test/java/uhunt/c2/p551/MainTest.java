package uhunt.c2.p551;

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
    public void fockdislaif() throws Exception {
        TestHelper.run(Main::main, "fockdislaif");
    }

    @Test
    @Timeout(3)
    public void matheus_aguilar() throws Exception {
        TestHelper.run(Main::main, "matheus_aguilar");
    }

    @Test
    @Timeout(3)
    public void morass() throws Exception {
        TestHelper.run(Main::main, "morass");
    }

    @Test
    @Timeout(3)
    public void uva() throws Exception {
        TestHelper.run(Main::main, "uva");
    }
}
