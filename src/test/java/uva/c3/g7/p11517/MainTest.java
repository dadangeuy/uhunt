package uva.c3.g7.p11517;

import uva.helper.TestHelper;
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
    public void brianfry713() throws Exception {
        TestHelper.run(Main::main, "brianfry713");
    }

    @Test
    @Timeout(1)
    public void rofi93_1() throws Exception {
        TestHelper.run(Main::main, "rofi93_1");
    }

    @Test
    @Timeout(1)
    public void rofi93_2() throws Exception {
        TestHelper.run(Main::main, "rofi93_2");
    }

    @Test
    @Timeout(1)
    public void nasher() throws Exception {
        TestHelper.run(Main::main, "nasher");
    }

    @Test
    @Timeout(1)
    public void rizaldi() throws Exception {
        TestHelper.run(Main::main, "rizaldi");
    }
}
