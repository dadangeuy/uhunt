package dev.rizaldi.uhunt.p10038;

import dev.rizaldi.uhunt.helper.TestFileHelper;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.URL;

public class MainTests {

    @Test
    public void example() throws Exception {
        String pkg = this.getClass().getPackage().getName();
        String problem = pkg.split("\\.")[3];
        URL testUrl = this.getClass().getResource(String.format("/%s", problem));
        File testDir = new File(testUrl.toURI());
        TestFileHelper.runTest(testDir, Main::main);
    }
}
