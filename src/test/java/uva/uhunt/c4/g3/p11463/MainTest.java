package uva.uhunt.c4.g3.p11463;

import uva.common.helper.TestHelper;
import org.junit.jupiter.api.Test;

public class MainTest {
    

    @Test
    public void sample() throws Exception {
        TestHelper.run(Main::main, "sample");
    }

    @Test
    public void batman() throws Exception {
        TestHelper.run(Main::main, "batman");
    }

    @Test
    public void uva() throws Exception {
        TestHelper.run(Main::main, "uva");
    }
}
