package uhunt.c2.g0.p230;

import uhunt.helper.TestHelper;
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
    public void mo7amed() throws Exception {
        TestHelper.run(Main::main, "mo7amed");
    }

    @Test
    @Timeout(3)
    public void alok1607() throws Exception {
        TestHelper.run(Main::main, "alok1607");
    }
}
