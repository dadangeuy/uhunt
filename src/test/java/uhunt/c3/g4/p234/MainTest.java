package uhunt.c3.g4.p234;

import org.junit.jupiter.api.Test;
import uhunt.helper.TestHelper;

public class MainTest {
    @Test
    public void sample() throws Exception {
        TestHelper.run(Main::main, "sample");
    }

    @Test
    public void batman() throws Exception {
        TestHelper.run(Main::main, "batman");
    }

    @Test
    public void goodeath() throws Exception {
        TestHelper.run(Main::main, "goodeath");
    }
}
