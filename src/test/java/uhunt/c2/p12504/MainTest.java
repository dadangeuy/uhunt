package uhunt.c2.p12504;

import uhunt.helper.TestHelper;
import org.junit.jupiter.api.Test;

public class MainTest {


    @Test
    public void sample() throws Exception {
        TestHelper.run(Main::main, "sample");
    }

    @Test
    public void _16321() throws Exception {
        TestHelper.run(Main::main, "16321");
    }

    @Test
    public void arash16() throws Exception {
        TestHelper.run(Main::main, "arash16");
    }

    @Test
    public void morass() throws Exception {
        TestHelper.run(Main::main, "morass");
    }
}
