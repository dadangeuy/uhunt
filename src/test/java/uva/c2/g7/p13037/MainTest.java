package uva.c2.g7.p13037;

import uva.helper.TestHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

public class MainTest {
    

    @Test
    @Timeout(2)
    public void sample() throws Exception {
        TestHelper.run(Main::main, "sample");
    }

    @Test
    @Timeout(2)
    public void udebug() throws Exception {
        TestHelper.run(Main::main, "udebug");
    }
}
