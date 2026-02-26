package uva.c3.g3.p1213;

import uva.helper.TestHelper;
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
    public void anjupiter() throws Exception {
        TestHelper.run(Main::main, "anjupiter");
    }

    @Test
    @Timeout(3)
    public void aunkon() throws Exception {
        TestHelper.run(Main::main, "aunkon");
    }

    @Test
    @Timeout(3)
    public void bartleby() throws Exception {
        TestHelper.run(Main::main, "bartleby");
    }
}
