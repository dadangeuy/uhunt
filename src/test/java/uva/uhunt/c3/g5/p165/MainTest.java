package uva.uhunt.c3.g5.p165;

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
    public void brianfry713() throws Exception {
        TestHelper.run(Main::main, "brianfry713");
    }
}
