package uva.uhunt.c3.g7.p307;

import uva.common.helper.TestHelper;
import org.junit.jupiter.api.Test;

public class MainTest {
    

    @Test
    public void sample() throws Exception {
        TestHelper.run(Main::main, "sample");
    }

    @Test
    public void szp() throws Exception {
        TestHelper.run(Main::main, "szp");
    }

    @Test
    public void uva() throws Exception {
        TestHelper.run(Main::main, "uva");
    }

    @Test
    public void rizaldi() throws Exception {
        TestHelper.run(Main::main, "rizaldi");
    }
}
