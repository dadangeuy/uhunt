package uhunt.c4.p590;

import uhunt.helper.TestHelper;
import org.junit.jupiter.api.Test;

public class MainTest {
    

    @Test
    public void sample() throws Exception {
        TestHelper.run(Main::main, "sample");
    }

    @Test
    public void dteo() throws Exception {
        TestHelper.run(Main::main, "dteo");
    }

    @Test
    public void skittles() throws Exception {
        TestHelper.run(Main::main, "skittles");
    }

    @Test
    public void jddantes() throws Exception {
        TestHelper.run(Main::main, "jddantes");
    }
}
