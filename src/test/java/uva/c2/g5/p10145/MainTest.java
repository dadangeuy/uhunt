package uva.c2.g5.p10145;

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
    public void goodeath_1() throws Exception {
        TestHelper.run(Main::main, "goodeath_1");
    }

    @Test
    @Timeout(3)
    public void goodeath_2() throws Exception {
        TestHelper.run(Main::main, "goodeath_2");
    }

    @Test
    @Timeout(3)
    public void ikadery() throws Exception {
        TestHelper.run(Main::main, "ikadery");
    }

    @Test
    @Timeout(3)
    public void dibery() throws Exception {
        TestHelper.run(Main::main, "dibery");
    }

    @Test
    @Timeout(3)
    public void kac3pro() throws Exception {
        TestHelper.run(Main::main, "kac3pro");
    }
}
