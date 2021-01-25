package vip.smartfamily.tools.http.exception;

/**
 * Socket 数据异常
 */
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
