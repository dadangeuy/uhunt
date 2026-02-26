package uva.c4.g9.p10259;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import uva.helper.TestHelper;

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
}
