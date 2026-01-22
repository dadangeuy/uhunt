package uhunt.c3.g8.p208;

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
    public void brianfry() throws Exception {
        TestHelper.run(Main::main, "brianfry");
    }

    @Test
    @Timeout(3)
    public void morass() throws Exception {
        TestHelper.run(Main::main, "morass");
    }

    @Test
    @Timeout(3)
    public void martian() throws Exception {
        TestHelper.run(Main::main, "martian");
    }

    @Test
//    @Timeout(3)
    public void rumman() throws Exception {
        TestHelper.run(Main::main, "rumman");
    }
}
