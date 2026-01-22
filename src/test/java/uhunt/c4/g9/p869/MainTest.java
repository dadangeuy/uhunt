package uhunt.c4.g9.p869;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import uhunt.helper.TestHelper;

@Nested
@Timeout(3)
public class MainTest {
    @Test
    public void sample() throws Exception {
        TestHelper.run(Main::main, "sample");
    }

    @Test
    public void fabikw() throws Exception {
        TestHelper.run(Main::main, "fabikw");
    }

    @Test
    public void txomin() throws Exception {
        TestHelper.run(Main::main, "txomin");
    }

    @Test
    public void rodrigo_1() throws Exception {
        TestHelper.run(Main::main, "rodrigo_1");
    }

    @Test
    public void rodrigo_2() throws Exception {
        TestHelper.run(Main::main, "rodrigo_2");
    }
}
