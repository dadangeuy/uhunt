package uhunt.c3.p12673;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import uhunt.helper.TestHelper;

@Nested
@Timeout(1)
public class MainTest {
    @Test
    public void sample() throws Exception {
        TestHelper.run(Main::main, "sample");
    }

    @Test
    public void anjupiter() throws Exception {
        TestHelper.run(Main::main, "anjupiter");
    }

    @Test
    public void feodorv_1() throws Exception {
        TestHelper.run(Main::main, "feodorv_1");
    }

    @Test
    public void feodorv_2() throws Exception {
        TestHelper.run(Main::main, "feodorv_2");
    }

    @Test
    public void shah() throws Exception {
        TestHelper.run(Main::main, "shah");
    }
}
