package uva.uhunt.c3.g0.p12190;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import uva.common.helper.TestHelper;

@Nested
@Timeout(3)
public class MainTest {
    @Test
    public void sample() throws Exception {
        TestHelper.run(Main::main, "sample");
    }

    @Test
    public void feodorv() throws Exception {
        TestHelper.run(Main::main, "feodorv_1", "feodorv_2", "feodorv_3");
    }

    @Test
    public void batman() throws Exception {
        TestHelper.run(Main::main, "batman");
    }
}
