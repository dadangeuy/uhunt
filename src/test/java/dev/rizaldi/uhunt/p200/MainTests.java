package dev.rizaldi.uhunt.p200;

import dev.rizaldi.uhunt.helper.TestFileHelper;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

public class MainTests {
    private final String packageName = getClass().getPackage().getName();
    private final String problem = packageName.replaceAll("[a-zA-Z0-9]+\\.", "");
    private final URI testURI = this.getClass().getResource(String.format("/%s", problem)).toURI();
    private final File testDirectory = new File(testURI);

    public MainTests() throws URISyntaxException {
    }

    @Test
    public void example1() throws Exception {
        TestFileHelper.runSingleTest(testDirectory, "1", Main::main);
    }

    @Test
    public void example2() throws Exception {
        TestFileHelper.runSingleTest(testDirectory, "2", Main::main);
    }

    @Test
    public void example3() throws Exception {
        TestFileHelper.runSingleTest(testDirectory, "3", Main::main);
    }
}
