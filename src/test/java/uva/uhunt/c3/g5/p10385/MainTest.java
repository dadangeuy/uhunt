package uva.uhunt.c3.g5.p10385;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import uva.common.helper.TestHelper;

@Nested
public class MainTest {
    @Test
    public void sample() throws Exception {
        TestHelper.run(Main::main, "sample");
    }

    @Test
    public void uva() throws Exception {
        TestHelper.run(Main::main, "uva");
    }

    @Test
    public void szp() throws Exception {
        TestHelper.run(Main::main, "szp");
    }
}
