package uva.uhunt.c1.g6.p10646;

import uva.common.helper.TestHelper;
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
    public void uva() throws Exception {
        TestHelper.run(Main::main, "uva");
    }

    @Test
    @Timeout(3)
    public void morass_1() throws Exception {
        TestHelper.run(Main::main, "morass_1");
    }

    @Test
    @Timeout(3)
    public void morass_2() throws Exception {
        TestHelper.run(Main::main, "morass_2");
    }

    @Test
    @Timeout(3)
    public void the_madman() throws Exception {
        TestHelper.run(Main::main, "the_madman");
    }
}
