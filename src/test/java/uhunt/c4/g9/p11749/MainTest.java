package uhunt.c4.g9.p11749;

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
    public void alberto() throws Exception {
        TestHelper.run(Main::main, "alberto");
    }

    @Test
    @Timeout(3)
    public void anjupiter() throws Exception {
        TestHelper.run(Main::main, "anjupiter");
    }

    @Test
    @Timeout(3)
    public void demislam() throws Exception {
        TestHelper.run(Main::main, "demislam");
    }

    @Test
    @Timeout(3)
    public void davizin() throws Exception {
        TestHelper.run(Main::main, "davizin1", "davizin2", "davizin3");
    }

    @Test
    @Timeout(3)
    public void rizaldi() throws Exception {
        TestHelper.run(Main::main, "rizaldi");
    }
}
