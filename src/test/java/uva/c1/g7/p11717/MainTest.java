package uva.c1.g7.p11717;

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
    public void batman() throws Exception {
        TestHelper.run(Main::main, "batman");
    }

    @Test
    @Timeout(1)
    public void nasher() throws Exception {
        TestHelper.run(Main::main, "nasher");
    }
}
