package uhunt.c3.g5.p1225;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import uhunt.helper.TestHelper;

@Nested
@Timeout(3)
public class MainTest {
    @Test
    public void sample() throws Exception {
        TestHelper.run(Main::main, "sample");
    }

    @Test
    public void monir() throws Exception {
        TestHelper.run(Main::main, "monir");
    }

    @Test
    public void ashik() throws Exception {
        TestHelper.run(Main::main, "ashik");
    }

    @Test
    public void niwesh() throws Exception {
        TestHelper.run(Main::main, "niwesh");
    }

    @Test
    public void andy() throws Exception {
        TestHelper.run(Main::main, "andy");
    }
}
