package uhunt.c3.p11742;

import uhunt.helper.TestHelper;
import org.junit.jupiter.api.Test;

public class MainTest {


    @Test
    public void sample() throws Exception {
        TestHelper.run(Main::main, "sample");
    }

    @Test
    public void nasher() throws Exception {
        TestHelper.run(Main::main, "nasher");
    }

    @Test
    public void govufpb() throws Exception {
        TestHelper.run(Main::main, "govufpb");
    }
}
