package uva.c4.g2.p852;

import uva.helper.TestHelper;
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
    public void fatemehkarimi() throws Exception {
        TestHelper.run(Main::main, "fatemehkarimi");
    }
}
