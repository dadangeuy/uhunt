package uhunt.c2.p414;

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
    public void udebug() throws Exception {
        TestHelper.run(Main::main, "udebug");
    }

    @Test
    @Timeout(3)
    public void shashank21j() throws Exception {
        TestHelper.run(Main::main, "shashank21j");
    }

    @Test
    @Timeout(3)
    public void ishtupeed() throws Exception {
        TestHelper.run(Main::main, "ishtupeed");
    }
}
