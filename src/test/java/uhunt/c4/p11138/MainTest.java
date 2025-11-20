package uhunt.c4.p11138;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import uhunt.helper.TestHelper;

public class MainTest {
    @Test
    @Timeout(3)
    public void sample() throws Exception {
        TestHelper.run(Main::main, "sample");
    }

    @Test
    @Timeout(3)
    public void anjupiter() throws Exception {
        TestHelper.run(Main::main, "anjupiter");
    }

    @Test
    @Timeout(3)
    public void rizaldi() throws Exception {
        TestHelper.run(Main::main, "rizaldi");
    }
}
