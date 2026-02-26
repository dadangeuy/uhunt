package uva.c3.g4.p10534;

import uva.helper.TestHelper;
import org.junit.jupiter.api.Test;

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
    public void batman() throws Exception {
        TestHelper.run(Main::main, "batman");
    }

    @Test
    public void rizaldi() throws Exception {
        TestHelper.run(Main::main, "rizaldi");
    }
}
