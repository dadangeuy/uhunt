package uva.c4.g5.p10765;

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
    public void fer22f_1() throws Exception {
        TestHelper.run(Main::main, "fer22f_1");
    }

    @Test
    @Timeout(3)
    public void fer22f_2() throws Exception {
        TestHelper.run(Main::main, "fer22f_2");
    }

    @Test
    public void rizaldi() throws Exception {
        TestHelper.run(Main::main, "rizaldi");
    }
}
