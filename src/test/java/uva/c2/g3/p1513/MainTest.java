package uva.c2.g3.p1513;

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
    public void anjupiter() throws Exception {
        TestHelper.run(Main::main, "anjupiter");
    }

    @Test
    @Timeout(3)
    public void pedropablo() throws Exception {
        TestHelper.run(Main::main, "pedropablo");
    }
}
