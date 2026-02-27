package uva.uhunt.c3.g0.p10660;

import org.junit.jupiter.api.Test;
import uva.common.helper.TestHelper;

public class MainTest {
    @Test
    public void sample() throws Exception {
        TestHelper.run(Main::main, "sample");
    }

    @Test
    public void brianfry() throws Exception {
        TestHelper.run(Main::main, "brianfry");
    }
}
