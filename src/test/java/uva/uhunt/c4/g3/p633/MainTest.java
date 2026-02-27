package uva.uhunt.c4.g3.p633;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import uva.common.helper.TestHelper;

public class MainTest {

    @Test
    @Timeout(3)
    public void sample() throws Exception {
        TestHelper.run(Main::main, "sample");
    }
}
