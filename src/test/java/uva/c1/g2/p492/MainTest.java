package uva.c1.g2.p492;

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
    public void udebug() throws Exception {
        TestHelper.run(Main::main, "udebug");
    }

    @Test
    @Timeout(3)
    public void shawon10() throws Exception {
        TestHelper.run(Main::main, "shawon10");
    }

    @Test
    @Timeout(3)
    public void sojolewu() throws Exception {
        TestHelper.run(Main::main, "sojolewu");
    }

    @Test
    @Timeout(3)
    public void akib_iiuc_1() throws Exception {
        TestHelper.run(Main::main, "akib_iiuc_1");
    }

    @Test
    @Timeout(3)
    public void akib_iiuc_2() throws Exception {
        TestHelper.run(Main::main, "akib_iiuc_2");
    }

    @Test
    @Timeout(3)
    public void akib_iiuc_3() throws Exception {
        TestHelper.run(Main::main, "akib_iiuc_3");
    }

    @Test
    @Timeout(3)
    public void eduardo_figueiredo() throws Exception {
        TestHelper.run(Main::main, "eduardo_figueiredo");
    }
}
