package uva.c3.g7.p1047;

import org.junit.jupiter.api.Test;
import uva.helper.TestHelper;

public class MainTest {
    @Test
    public void sample() throws Exception {
        TestHelper.run(Main::main, "sample");
    }

    @Test
    public void brianfry() throws Exception {
        TestHelper.run(Main::main, "brianfry");
    }

    @Test
    public void ekangas() throws Exception {
        TestHelper.run(Main::main, "ekangas");
    }

    @Test
    public void txomin() throws Exception {
        TestHelper.run(Main::main, "txomin");
    }

    @Test
    public void udebug() throws Exception {
        TestHelper.run(Main::main, "udebug");
    }
}
