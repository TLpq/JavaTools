package vip.smartfamily.tools.http.exception;

public class HttpDataException extends Exception {
    public HttpDataException() {
    }

    public HttpDataException(String message) {
        super(message);
    }

    public HttpDataException(Throwable cause) {
        super(cause);
    }
}
