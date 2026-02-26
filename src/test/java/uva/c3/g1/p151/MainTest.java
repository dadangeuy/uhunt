package uva.c3.g1.p151;

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
    public void morass() throws Exception {
        TestHelper.run(Main::main, "morass");
    }

    @Test
    @Timeout(3)
    public void kishor_aiub() throws Exception {
        TestHelper.run(Main::main, "kishor_aiub");
    }

    @Test
    @Timeout(3)
    public void eduardo_figueiredo() throws Exception {
        TestHelper.run(Main::main, "eduardo_figueiredo");
    }
}
