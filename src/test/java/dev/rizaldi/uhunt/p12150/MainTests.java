package dev.rizaldi.uhunt.p12150;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.Charset;

public class MainTests {

    @Test
    public void example() throws Exception {
        int totalTest = 1;
        for (int testcase = 1; testcase <= totalTest; testcase++) {
            String inputPath = String.format("/p12150/%d.in", testcase);
            InputStream inputStream = this.getClass().getResourceAsStream(inputPath);
            System.setIn(inputStream);

            String outputPath = String.format("/p12150/%d.out", testcase);
            InputStream outputStream = this.getClass().getResourceAsStream(outputPath);
            String output = IOUtils.toString(outputStream, Charset.defaultCharset());

            ByteArrayOutputStream actualOutputStream = new ByteArrayOutputStream();
            System.setOut(new PrintStream(actualOutputStream));

            Main.main();

            String actualOutput = actualOutputStream.toString();

            Assertions.assertEquals(output, actualOutput);
        }
    }
}
