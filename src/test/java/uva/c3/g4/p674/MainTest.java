package uva.c3.g4.p674;

import uva.helper.TestHelper;
import org.junit.jupiter.api.Test;

public class MainTest {
    

    @Test
    public void sample() throws Exception {
        TestHelper.run(Main::main, "sample");
    }

    @Test
    public void uva() throws Exception {
        TestHelper.run(Main::main, "uva");
    }

    @Test
    public void feodorv() throws Exception {
        TestHelper.run(Main::main, "feodorv");
    }
}
