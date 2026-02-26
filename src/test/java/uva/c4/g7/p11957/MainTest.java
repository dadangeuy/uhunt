package uva.c4.g7.p11957;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import uva.helper.TestHelper;

@Nested
@Timeout(1)
public class MainTest {
    @Test
    public void sample() throws Exception {
        TestHelper.run(Main::main, "sample");
    }

    @Test
    public void anjupiter() throws Exception {
        TestHelper.run(Main::main, "anjupiter");
    }

    @Test
    public void morass() throws Exception {
        TestHelper.run(Main::main, "morass");
    }
}
