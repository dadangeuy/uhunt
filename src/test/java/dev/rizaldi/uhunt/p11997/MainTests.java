package dev.rizaldi.uhunt.p11997;

import dev.rizaldi.uhunt.helper.TestFileHelper;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.URL;

public class MainTests {
    private final String problem = getClass().getPackage().getName().replaceFirst(".*(?=p[0-9]+$)", "");

    @Test
    public void example() throws Exception {
        URL testUrl = getClass().getResource(String.format("/%s", problem));
        File testDir = new File(testUrl.toURI());
        TestFileHelper.runTest(testDir, Main::main);
    }
}
