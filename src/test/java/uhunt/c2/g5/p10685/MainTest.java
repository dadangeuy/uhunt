package uhunt.c2.g5.p10685;

import uhunt.helper.TestHelper;
import org.junit.jupiter.api.Test;

public class MainTest {
    

    @Test
    public void sample() throws Exception {
        TestHelper.run(Main::main, "sample");
    }

    @Test
    public void debugster() throws Exception {
        TestHelper.run(Main::main, "debugster");
    }

    @Test
    public void hallway() throws Exception {
        TestHelper.run(Main::main, "hallway");
    }

    @Test
    public void md_ashif313() throws Exception {
        TestHelper.run(Main::main, "md.ashif313");
    }

    @Test
    public void morass() throws Exception {
        TestHelper.run(Main::main, "morass");
    }

    @Test
    public void uva() throws Exception {
        TestHelper.run(Main::main, "uva");
    }
}
