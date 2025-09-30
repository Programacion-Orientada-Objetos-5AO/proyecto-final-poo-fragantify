package ar.edu.huergo.lbgonzalez.fragantify.exception;

/**
 * Excepción utilizada cuando la API pública de Fragella no puede ser consultada
 * o devuelve un formato inesperado.
 */
public class FragellaApiException extends RuntimeException {

    public FragellaApiException(String message) {
        super(message);
    }

    public FragellaApiException(String message, Throwable cause) {
        super(message, cause);
    }
}
