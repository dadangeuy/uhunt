package uva.c4.g0.p670;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import uva.helper.TestHelper;

@Nested
@Timeout(3)
public class MainTest {
    @Test
    @Disabled("accepted result, but output is non-deterministic")
    public void sample() throws Exception {
        TestHelper.run(Main::main, "sample");
    }

    @Test
    @Disabled("accepted result, but output is non-deterministic")
    public void morass() throws Exception {
        TestHelper.run(Main::main, "morass");
    }

    @Test
    public void marcoa() throws Exception {
        TestHelper.run(Main::main, "marcoa");
    }
}
