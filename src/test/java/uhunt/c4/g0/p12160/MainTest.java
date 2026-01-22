package uhunt.c4.g0.p12160;

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
    public void morass() throws Exception {
        TestHelper.run(Main::main, "morass");
    }

    @Test
    @Timeout(3)
    public void nasher() throws Exception {
        TestHelper.run(Main::main, "nasher");
    }

    @Test
    @Timeout(3)
    public void turin101_1() throws Exception {
        TestHelper.run(Main::main, "turin101_1");
    }

    @Test
    @Timeout(3)
    public void turin101_2() throws Exception {
        TestHelper.run(Main::main, "turin101_2");
    }
}
