package uhunt.helper;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Assertions;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.Duration;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;

public class TestHelper {
    public static void run(final Duration timeout, final ThrowingRunnable main, final String... caseIds) throws Exception {
        final File directory = getDirectory(main.getClass());
        for (final String file : caseIds) {
            final File input = new File(directory, file + ".i");
            final File output = new File(directory, file + ".o");
            final File result = new File(directory, file + ".r");

            final InputStream inputStream = new FileInputStream(input);
            final PrintStream resultStream = new PrintStream(result);

            System.setIn(inputStream);
            System.setOut(resultStream);

            assertTimeoutPreemptively(timeout, () -> main.run(), "Verdict: Time Limit Exceeded");

            final boolean match = FileUtils.contentEqualsIgnoreEOL(output, result, null);
            Assertions.assertTrue(match, "Verdict: Wrong Answer");
        }
    }

    public static void run(final ThrowingRunnable main, final String... caseIds) throws Exception {
        run(Duration.ofMinutes(1), main, caseIds);
    }

    public static File getDirectory(final Class<?> klass) throws URISyntaxException {
        final String packageName = klass.getPackage().getName();
        final String packagePath = packageName.replaceAll("\\.", "/");
        final String testcasePath = packagePath.replaceFirst("uhunt/", "/testcase/");
        final URL testURL = Objects.requireNonNull(klass.getResource(testcasePath));
        final URI testURI = testURL.toURI();
        return new File(testURI);
    }
}
