package uhunt.c2.g7.p127;

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
    public void ksqf() throws Exception {
        TestHelper.run(Main::main, "ksqf");
    }

    @Test
    @Timeout(3)
    public void rizaldi_1() throws Exception {
        TestHelper.run(Main::main, "rizaldi_1");
    }
}
