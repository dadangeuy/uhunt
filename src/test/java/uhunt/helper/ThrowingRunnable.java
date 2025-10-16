package uhunt.helper;

@FunctionalInterface
public interface ThrowingRunnable {
    void run(String... args) throws Exception;
}
