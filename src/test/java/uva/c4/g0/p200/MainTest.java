package uva.c4.g0.p200;

import uva.helper.TestHelper;
import org.junit.jupiter.api.Test;

public class MainTest {
    

    @Test
    public void example1() throws Exception {
        TestHelper.run(Main::main, "1");
    }

    @Test
    public void example2() throws Exception {
        TestHelper.run(Main::main, "2");
    }

    @Test
    public void example3() throws Exception {
        TestHelper.run(Main::main, "3");
    }
}
