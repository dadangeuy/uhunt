package uhunt.c2.p727;

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
    public void trycatchme() throws Exception {
        TestHelper.run(Main::main, "trycatchme");
    }

    @Test
    @Timeout(3)
    public void debugster() throws Exception {
        TestHelper.run(Main::main, "debugster");
    }

    @Test
    public void mycodeschool() throws Exception {
        TestHelper.run(Main::main, "mycodeschool");
    }
}
