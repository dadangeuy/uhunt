package uva.uhunt.c2.g4.p394;

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
    public void brianfry() throws Exception {
        TestHelper.run(Main::main, "brianfry");
    }

    @Test
    @Timeout(3)
    public void uva() throws Exception {
        TestHelper.run(Main::main, "uva");
    }
//
//    @Test
//    @Timeout(3)
//    public void marcoa() throws Exception {
//        TestHelper.run(Main::main, testDirectory, "marcoa");
//    }
}
