package uhunt.c1.p12372;

import uhunt.helper.TestHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

public class MainTest {


    @Test
    @Timeout(3)
    public void sample() throws Exception {
        TestHelper.run(Main::main, "sample");
    }
}
