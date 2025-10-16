package uhunt.c4.p633;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import uhunt.helper.TestHelper;

public class MainTest {

    @Test
    @Timeout(3)
    public void sample() throws Exception {
        TestHelper.run(Main::main, "sample");
    }
}
