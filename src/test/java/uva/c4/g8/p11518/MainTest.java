package uva.c4.g8.p11518;

import uva.helper.TestHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

public class MainTest {


    @Test
    @Timeout(2)
    public void sample() throws Exception {
        TestHelper.run(Main::main, "sample");
    }

    @Test
    @Timeout(2)
    public void brianfry713() throws Exception {
        TestHelper.run(Main::main, "brianfry713");
    }

    @Test
    @Timeout(2)
    public void faisal() throws Exception {
        TestHelper.run(Main::main, "faisal");
    }
}
