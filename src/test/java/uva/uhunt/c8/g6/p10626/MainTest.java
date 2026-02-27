package uva.uhunt.c8.g6.p10626;

import org.junit.jupiter.api.Test;
import uva.common.helper.TestHelper;

import java.time.Duration;

public class MainTest {
    private static final Duration timeout = Duration.ofSeconds(3);

    @Test
    public void sample() throws Exception {
        TestHelper.run(timeout, Main::main, "sample");
    }

    @Test
    public void rizaldi() throws Exception {
        TestHelper.run(timeout, Main::main, "rizaldi");
    }

    @Test
    public void marcoa() throws Exception {
        TestHelper.run(Main::main, "marcoa");
    }

    @Test
    public void hao_1() throws Exception {
        TestHelper.run(timeout, Main::main, "hao_1");
    }

    @Test
    public void hao_2() throws Exception {
        TestHelper.run(timeout, Main::main, "hao_2");
    }
}
