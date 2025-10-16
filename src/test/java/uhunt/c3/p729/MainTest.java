package uhunt.c3.p729;

import uhunt.helper.TestHelper;
import org.junit.jupiter.api.Test;

public class MainTest {
    

    @Test
    public void sample() throws Exception {
        TestHelper.run(Main::main, "sample");
    }

    @Test
    public void anjupiter() throws Exception {
        TestHelper.run(Main::main, "anjupiter");
    }

    @Test
    public void zayed() throws Exception {
        TestHelper.run(Main::main, "zayed");
    }

    @Test
    public void sahidcseku() throws Exception {
        TestHelper.run(Main::main, "sahidcseku");
    }
}
