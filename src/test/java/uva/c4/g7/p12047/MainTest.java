package uva.c4.g7.p12047;

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
    @Timeout(3)
    public void ashif() throws Exception {
        TestHelper.run(Main::main, "ashif1", "ashif2");
    }

    @Test
//    @Timeout(3)
    public void fatemehkarimi() throws Exception {
        TestHelper.run(Main::main, "fatemehkarimi");
    }

    @Test
    @Timeout(3)
    public void gautham() throws Exception {
        TestHelper.run(Main::main, "gautham");
    }
}
