package uva.c2.g7.p417;

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
    public void feodorv() throws Exception {
        TestHelper.run(Main::main, "feodorv");
    }

    @Test
    @Timeout(3)
    public void nakar81() throws Exception {
        TestHelper.run(Main::main, "nakar81");
    }

    @Test
    @Timeout(3)
    public void shah_shishir() throws Exception {
        TestHelper.run(Main::main, "shah_shishir");
    }

    @Test
    @Timeout(3)
    public void udebug() throws Exception {
        TestHelper.run(Main::main, "udebug");
    }
}
