package uva.common.helper;

@FunctionalInterface
public interface ThrowingRunnable {
    void run(String... args) throws Exception;
}
