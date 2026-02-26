package uva.c3.g9.p11369;

import uva.helper.TestHelper;
import org.junit.jupiter.api.Test;

public class MainTest {
    

    @Test
    public void sample() throws Exception {
        TestHelper.run(Main::main, "sample");
    }

    @Test
    public void morass() throws Exception {
        TestHelper.run(Main::main, "morass");
    }

    @Test
    public void pedropablo() throws Exception {
        TestHelper.run(Main::main, "pedropablo");
    }
}
