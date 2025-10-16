package uhunt.c2.p11423;

import uhunt.helper.TestHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

public class MainTest {


    @Test
    @Timeout(10)
    public void sample() throws Exception {
        TestHelper.run(Main::main, "sample");
    }

    @Test
    @Timeout(10)
    public void essux() throws Exception {
        TestHelper.run(Main::main, "essux");
    }
}
