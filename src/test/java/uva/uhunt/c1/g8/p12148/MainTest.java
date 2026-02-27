package uva.uhunt.c1.g8.p12148;

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
    public void shashank21j() throws Exception {
        TestHelper.run(Main::main, "shashank21j");
    }

    @Test
    @Timeout(3)
    public void batman() throws Exception {
        TestHelper.run(Main::main, "batman");
    }

    @Test
    @Timeout(3)
    public void zex() throws Exception {
        TestHelper.run(Main::main, "zex");
    }
}
