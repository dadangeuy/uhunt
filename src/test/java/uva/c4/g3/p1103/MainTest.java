package uva.c4.g3.p1103;

import uva.helper.TestHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

public class MainTest {



    @Test
    @Timeout(3)
    public void uva() throws Exception {
        TestHelper.run(Main::main, "uva");
    }

    @Test
    @Timeout(3)
    public void morass() throws Exception {
        TestHelper.run(Main::main, "morass");
    }

    @Test
    @Timeout(3)
    public void y1er() throws Exception {
        TestHelper.run(Main::main, "y1er");
    }

    @Test
    @Timeout(3)
    public void rizaldi() throws Exception {
        TestHelper.run(Main::main, "rizaldi");
    }
}
