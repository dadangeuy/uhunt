package dev.rizaldi.uhunt.c2.p12049;

import dev.rizaldi.uhunt.helper.TestFileHelper;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.URL;

public class MainTests {

    @Test
    public void fileTest() throws Exception {
        String pkg = this.getClass().getPackage().getName();
        String problem = pkg.split("\\.")[3];
        URL testUrl = this.getClass().getResource(String.format("/%s", problem));
        File testDir = new File(testUrl.toURI());
        TestFileHelper.runTest(testDir, () -> {
                    try {
                        Main.main();
                    } catch (Exception e) {
                    }
                }
        );
    }
}
