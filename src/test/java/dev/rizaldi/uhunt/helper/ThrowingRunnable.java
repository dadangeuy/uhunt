package dev.rizaldi.uhunt.helper;

@FunctionalInterface
public interface ThrowingRunnable {
    void run() throws Exception;
}
