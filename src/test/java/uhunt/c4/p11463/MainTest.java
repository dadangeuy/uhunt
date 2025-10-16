package uhunt.c4.p11463;

import uhunt.helper.TestHelper;
import org.junit.jupiter.api.Test;

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
    public void uva() throws Exception {
        TestHelper.run(Main::main, "uva");
    }
}
