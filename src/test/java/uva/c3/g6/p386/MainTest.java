package uva.c3.g6.p386;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import uva.helper.TestHelper;

public class MainTest {
    @Test
    @Timeout(3)
    public void udebug() throws Exception {
        TestHelper.run(Main::main, "udebug");
    }
}
