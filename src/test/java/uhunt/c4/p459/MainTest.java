package uhunt.c4.p459;

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
}
