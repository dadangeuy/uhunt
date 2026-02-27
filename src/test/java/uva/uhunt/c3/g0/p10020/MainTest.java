package uva.uhunt.c3.g0.p10020;

import uva.common.helper.TestHelper;
import org.junit.jupiter.api.Test;

public class MainTest {
    

    @Test
    public void sample() throws Exception {
        TestHelper.run(Main::main, "sample");
    }

    @Test
    public void morass() throws Exception {
        TestHelper.run(Main::main, "morass");
    }

    @Test
    public void brianfry713() throws Exception {
        TestHelper.run(Main::main, "brianfry713");
    }
}
