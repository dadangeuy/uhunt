package uhunt.c1.p12085;

import uhunt.helper.TestHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

public class MainTest {
    

    @Test
    @Timeout(3)
    public void sample() throws Exception {
        TestHelper.run(Main::main, "sample");
    }

    @Test
    @Timeout(3)
    public void alhunor() throws Exception {
        TestHelper.run(Main::main, "alhunor");
    }

    @Test
    @Timeout(3)
    public void bryton() throws Exception {
        TestHelper.run(Main::main, "bryton");
    }
}
