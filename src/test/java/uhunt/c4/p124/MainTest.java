package uhunt.c4.p124;

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
    public void uva() throws Exception {
        TestHelper.run(Main::main, "uva");
    }

    @Test
    @Timeout(3)
    public void rafastv() throws Exception {
        TestHelper.run(Main::main, "rafastv");
    }

    @Test
    @Timeout(3)
    public void twyu() throws Exception {
        TestHelper.run(Main::main, "twyu");
    }
}
