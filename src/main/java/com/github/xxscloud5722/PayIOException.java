package com.github.xxscloud5722;
/**
 * @author Cat.
 */
public class PayIOException extends RuntimeException {
    public PayIOException() {
        super();
    }

    public PayIOException(String message) {
        super(message);
    }

    public PayIOException(String message, Throwable cause) {
        super(message, cause);
    }

    public PayIOException(Throwable cause) {
        super(cause);
    }

    protected PayIOException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
