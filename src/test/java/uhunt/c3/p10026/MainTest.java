package uhunt.c3.p10026;

import org.junit.jupiter.api.Test;
import uhunt.helper.TestHelper;

public class MainTest {
    @Test
    public void sample() throws Exception {
        TestHelper.run(Main::main, "sample");
    }

    @Test
    public void brianfry() throws Exception {
        TestHelper.run(Main::main, "brianfry");
    }
}
