package uhunt.c4.p12347;

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
    public void bryton() throws Exception {
        TestHelper.run(Main::main, "bryton");
    }

    @Test
    public void feodorv() throws Exception {
        TestHelper.run(Main::main, "feodorv");
    }

    @Test
    public void marcoa_1() throws Exception {
        TestHelper.run(Main::main, "marcoa_1");
    }

    @Test
    public void marcoa_2() throws Exception {
        TestHelper.run(Main::main, "marcoa_2");
    }

    @Test
    public void marcoa_3() throws Exception {
        TestHelper.run(Main::main, "marcoa_3");
    }
}
