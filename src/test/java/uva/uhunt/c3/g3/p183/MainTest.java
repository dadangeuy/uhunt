package uva.uhunt.c3.g3.p183;

import uva.common.helper.TestHelper;
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
    public void morass1() throws Exception {
        TestHelper.run(Main::main, "morass_1");
    }

    @Test
    @Timeout(3)
    public void morass2() throws Exception {
        TestHelper.run(Main::main, "morass_2");
    }
}
