package uhunt.c2.p246;

import uhunt.helper.TestHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

public class MainTest {
    

    @Test
    @Timeout(3)
    public void sample() throws Exception {
        TestHelper.run(Main::main, "sample");
    }

    @Test
    @Timeout(3)
    public void mabius() throws Exception {
        TestHelper.run(Main::main, "mabius");
    }

    @Test
    public void rizaldi() throws Exception {
        TestHelper.run(Main::main, "rizaldi");
    }
}
