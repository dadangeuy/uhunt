package uhunt.c4.g7.p11747;

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
    public void dibery() throws Exception {
        TestHelper.run(Main::main, "dibery");
    }

    @Test
    @Timeout(3)
    public void anonymous() throws Exception {
        TestHelper.run(Main::main, "anonymous");
    }

    @Test
    @Timeout(3)
    public void ansisg() throws Exception {
        TestHelper.run(Main::main, "ansisg");
    }
}
