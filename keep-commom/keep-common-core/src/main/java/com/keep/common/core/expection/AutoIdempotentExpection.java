package com.keep.common.core.expection;

public class AutoIdempotentExpection extends RootRuntimeException{

    public AutoIdempotentExpection(String message) {
        super(message);
    }

    public AutoIdempotentExpection(Integer exceptionCode, String message) {
        super(exceptionCode, message);
    }

    public AutoIdempotentExpection(Integer exceptionCode, String message, Throwable cause) {
        super(exceptionCode, message, cause);
    }
}
