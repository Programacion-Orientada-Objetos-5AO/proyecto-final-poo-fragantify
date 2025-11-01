package ar.edu.huergo.lbgonzalez.fragantify.exception;

public class PerfumeNotFoundException extends RuntimeException {
    public PerfumeNotFoundException(String message) {
        super(message);
    }

    public PerfumeNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
