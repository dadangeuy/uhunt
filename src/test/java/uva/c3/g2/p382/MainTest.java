package uva.c3.g2.p382;

import org.junit.jupiter.api.Test;
import uva.helper.TestHelper;

public class MainTest {
    @Test
    public void sample() throws Exception {
        TestHelper.run(Main::main, "sample");
    }

    @Test
    public void uva() throws Exception {
        TestHelper.run(Main::main, "uva");
    }

    @Test
    public void debugster() throws Exception {
        TestHelper.run(Main::main, "debugster");
    }

    @Test
    public void niwesh() throws Exception {
        TestHelper.run(Main::main, "niwesh");
    }

    @Test
    public void gmori() throws Exception {
        TestHelper.run(Main::main, "gmori");
    }
}
