package com.boardproject.jpashop.exception;

public class NeedMoreException extends RuntimeException{

    public NeedMoreException() {
        super();
    }

    public NeedMoreException(String message) {
        super(message);
    }

    public NeedMoreException(String message, Throwable cause) {
        super(message, cause);
    }

    public NeedMoreException(Throwable cause) {
        super(cause);
    }

    protected NeedMoreException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
