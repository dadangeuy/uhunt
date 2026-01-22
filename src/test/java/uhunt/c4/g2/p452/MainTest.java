package uhunt.c4.g2.p452;

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
    public void brianfry() throws Exception {
        TestHelper.run(Main::main, "brianfry");
    }

    @Test
    @Timeout(3)
    public void morass() throws Exception {
        TestHelper.run(Main::main, "morass");
    }

    @Test
    @Timeout(3)
    public void mutska() throws Exception {
        TestHelper.run(Main::main, "mutska");
    }

    @Test
    @Timeout(3)
    public void snowycoder() throws Exception {
        TestHelper.run(Main::main, "snowycoder");
    }

    @Test
    @Timeout(3)
    public void talha() throws Exception {
        TestHelper.run(Main::main, "talha");
    }
}
