package uva.c2.g8.p13048;

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
    public void udebug() throws Exception {
        TestHelper.run(Main::main, "udebug");
    }

    @Test
    @Timeout(1)
    public void feodorv() throws Exception {
        TestHelper.run(Main::main, "feodorv");
    }
}
