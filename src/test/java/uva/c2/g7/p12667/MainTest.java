package uva.c2.g7.p12667;

import uva.helper.TestHelper;
import org.junit.jupiter.api.Test;

public class MainTest {
    

    @Test
    public void sample() throws Exception {
        TestHelper.run(Main::main, "sample");
    }

    @Test
    public void anjupiter() throws Exception {
        TestHelper.run(Main::main, "anjupiter");
    }

    @Test
    public void udebug() throws Exception {
        TestHelper.run(Main::main, "udebug");
    }
}
