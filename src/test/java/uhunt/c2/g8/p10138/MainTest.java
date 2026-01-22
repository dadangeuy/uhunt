package uhunt.c2.g8.p10138;

import uhunt.helper.TestHelper;
import org.junit.jupiter.api.Test;

public class MainTest {
    

    @Test
    public void case1() throws Exception {
        TestHelper.run(Main::main, "1");
    }

    @Test
    public void case2() throws Exception {
        TestHelper.run(Main::main, "2");
    }

    @Test
    public void case3() throws Exception {
        TestHelper.run(Main::main, "3");
    }

    @Test
    public void case4() throws Exception {
        TestHelper.run(Main::main, "4");
    }
}
