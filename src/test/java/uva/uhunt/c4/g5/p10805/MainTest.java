package uva.uhunt.c4.g5.p10805;

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
    public void john() throws Exception {
        TestHelper.run(Main::main, "john");
    }

    @Test
    public void uva() throws Exception {
        TestHelper.run(Main::main, "uva");
    }

    @Test
    public void cde() throws Exception {
        TestHelper.run(Main::main, "cde");
    }
}
