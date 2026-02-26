package uva.c1.g4.p1124;

import java.io.IOException;

public class Main {
    public static void main(String... args) throws IOException {
        byte[] buffer = new byte[1024];
        while (System.in.available() > 0) {
            int len = System.in.read(buffer);
            System.out.write(buffer, 0, len);
        }

        System.in.close();
        System.out.flush();
        System.out.close();
    }
}
