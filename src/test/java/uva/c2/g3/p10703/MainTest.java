package uva.c2.g3.p10703;

import uva.helper.TestHelper;
import org.junit.jupiter.api.Test;

public class MainTest {
    

    @Test
    public void sample() throws Exception {
        TestHelper.run(Main::main, "sample");
    }

    @Test
    public void udebug() throws Exception {
        TestHelper.run(Main::main, "udebug");
    }

    @Test
    public void turin101() throws Exception {
        TestHelper.run(Main::main, "turin101");
    }
}
