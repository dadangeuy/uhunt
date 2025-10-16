package uhunt.c2.p10887;

import uhunt.helper.TestHelper;
import org.junit.jupiter.api.Test;

public class MainTest {


    @Test
    public void example() throws Exception {
        TestHelper.run(Main::main, "1", "2", "3", "4");
    }
}
