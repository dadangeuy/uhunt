package uhunt.c3.g9.p13109;

import uhunt.helper.TestHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

public class MainTest {


    @Test
    @Timeout(2)
    public void sample() throws Exception {
        TestHelper.run(Main::main, "sample");
    }

    @Test
    @Timeout(2)
    public void anjupiter() throws Exception {
        TestHelper.run(Main::main, "anjupiter");
    }

    @Test
    @Timeout(2)
    public void ak94() throws Exception {
        TestHelper.run(Main::main, "ak94");
    }

    @Test
    @Timeout(2)
    public void ajunior() throws Exception {
        TestHelper.run(Main::main, "ajunior");
    }
}
