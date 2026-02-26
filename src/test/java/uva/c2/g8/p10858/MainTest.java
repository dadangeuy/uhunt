package uva.c2.g8.p10858;

import uva.helper.TestHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

public class MainTest {


    @Test
    @Timeout(3)
    public void sample() throws Exception {
        TestHelper.run(Main::main, "sample");
    }

    @Test
//    @Timeout(3)
    public void morass() throws Exception {
        TestHelper.run(Main::main, "morass");
    }
}
