package uva.uhunt.c4.g7.p907;

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
    public void morass() throws Exception {
        TestHelper.run(Main::main, "morass");
    }

    @Test
    public void marcoa() throws Exception {
        TestHelper.run(Main::main, "marcoa");
    }
}
