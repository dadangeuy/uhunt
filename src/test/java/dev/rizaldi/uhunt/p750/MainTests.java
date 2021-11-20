package dev.rizaldi.uhunt.p750;

import dev.rizaldi.uhunt.helper.TestFileHelper;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.URI;

public class MainTests {
    private final String packageName = getClass().getPackage().getName();
    private final String problem = packageName.replaceAll("[a-zA-Z0-9]+\\.", "");

    @Test
    public void example1() throws Exception {
        URI testURI = this.getClass().getResource(String.format("/%s", problem)).toURI();
        File testDirectory = new File(testURI);
        TestFileHelper.runSingleTest(testDirectory, "1", Main::main);
    }

    @Test
    public void example2() throws Exception {
        URI testURI = this.getClass().getResource(String.format("/%s", problem)).toURI();
        File testDirectory = new File(testURI);
        TestFileHelper.runSingleTest(testDirectory, "2", Main::main);
    }
}
