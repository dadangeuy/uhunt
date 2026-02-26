package uva.c3.g4.p12324;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import uva.helper.TestHelper;

@Nested
@Timeout(2)
public class MainTest {
    @Test
    public void sample() throws Exception {
        TestHelper.run(Main::main, "sample");
    }

    @Test
    public void anjupiter() throws Exception {
        TestHelper.run(Main::main, "anjupiter");
    }
}
