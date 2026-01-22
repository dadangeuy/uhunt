package uhunt.c4.g4.p10054;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import uhunt.helper.TestHelper;

@Nested
@Timeout(3)
public class MainTest {
    @Test
    public void sample() throws Exception {
        TestHelper.run(Main::main, "sample");
    }

    @Test
    public void pedro_1() throws Exception {
        TestHelper.run(Main::main, "pedro_1");
    }

    @Test
    @Disabled("accepted result, but output is non-deterministic")
    public void pedro_2() throws Exception {
        TestHelper.run(Main::main, "pedro_2");
    }

    @Test
    @Disabled("accepted result, but output is non-deterministic")
    public void brian() throws Exception {
        TestHelper.run(Main::main, "brian");
    }

    @Test
    public void psz_1() throws Exception {
        TestHelper.run(Main::main, "psz_1");
    }

    @Test
    public void psz_2() throws Exception {
        TestHelper.run(Main::main, "psz_2");
    }
}
