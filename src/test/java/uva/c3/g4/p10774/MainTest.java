package uva.c3.g4.p10774;

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
    public void anjupiter() throws Exception {
        TestHelper.run(Main::main, "anjupiter");
    }

    @Test
    public void executioner() throws Exception {
        TestHelper.run(Main::main, "executioner");
    }
}
