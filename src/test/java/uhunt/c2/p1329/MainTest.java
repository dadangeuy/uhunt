package uhunt.c2.p1329;

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
    public void goodeath_1() throws Exception {
        TestHelper.run(Main::main, "goodeath_1");
    }

    @Test
    @Timeout(3)
    public void goodeath_2() throws Exception {
        TestHelper.run(Main::main, "goodeath_2");
    }

    @Test
    @Timeout(3)
    public void goodeath_3() throws Exception {
        TestHelper.run(Main::main, "goodeath_3");
    }

    @Test
    @Timeout(3)
    public void goodeath_4() throws Exception {
        TestHelper.run(Main::main, "goodeath_4");
    }
}
