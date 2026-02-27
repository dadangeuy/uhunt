package uva.uhunt.c3.g2.p662;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import uva.common.helper.TestHelper;

@Nested
public class MainTest {
    @Test
    public void sample() throws Exception {
        TestHelper.run(Main::main, "sample");
    }

    @Test
    public void uva() throws Exception {
        TestHelper.run(Main::main, "uva");
    }
}
