package uhunt.c2.p12049;

import uhunt.helper.TestHelper;
import org.junit.jupiter.api.Test;

public class MainTest {


    @Test
    public void fileTest() throws Exception {
        TestHelper.run(Main::main, "1", "2", "3");
    }
}
