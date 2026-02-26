package uva.c2.g3.p11423;

import uva.helper.TestHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

public class MainTest {


    @Test
    @Timeout(10)
    public void sample() throws Exception {
        TestHelper.run(Main::main, "sample");
    }

    @Test
    @Timeout(10)
    public void essux() throws Exception {
        TestHelper.run(Main::main, "essux");
    }
}
