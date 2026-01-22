package uhunt.c3.g8.p10908;

import org.junit.jupiter.api.Test;
import uhunt.helper.TestHelper;

public class MainTest {
    @Test
    public void sample() throws Exception {
        TestHelper.run(Main::main, "sample");
    }

    @Test
    public void pedro() throws Exception {
        TestHelper.run(Main::main, "pedro");
    }

    @Test
    public void uva() throws Exception {
        TestHelper.run(Main::main, "uva");
    }
}
