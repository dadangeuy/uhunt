package uva.uhunt.c4.g6.p536;

import uva.common.helper.TestHelper;
import org.junit.jupiter.api.Test;

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
    public void hquilo() throws Exception {
        TestHelper.run(Main::main, "hquilo");
    }
}
