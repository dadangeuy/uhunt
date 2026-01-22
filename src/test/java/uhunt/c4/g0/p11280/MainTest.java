package uhunt.c4.g0.p11280;

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
    public void anasschoukri() throws Exception {
        TestHelper.run(Main::main, "anasschoukri");
    }

    @Test
    @Timeout(3)
    public void batman() throws Exception {
        TestHelper.run(Main::main, "batman");
    }

    @Test
    @Timeout(3)
    public void brianfry713() throws Exception {
        TestHelper.run(Main::main, "brianfry713");
    }

    @Test
    @Timeout(3)
    public void dvrodri8() throws Exception {
        TestHelper.run(Main::main, "dvrodri8");
    }

    @Test
    @Timeout(3)
    public void fatemehkarimi() throws Exception {
        TestHelper.run(Main::main, "fatemehkarimi");
    }

    @Test
    public void rizaldi() throws Exception {
        TestHelper.run(Main::main, "rizaldi");
    }
}
