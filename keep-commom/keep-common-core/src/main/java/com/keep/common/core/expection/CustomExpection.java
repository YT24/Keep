package com.keep.common.core.expection;

public class CustomExpection extends RootRuntimeException{

    public CustomExpection(String message) {
        super(message);
    }

    public CustomExpection(Integer exceptionCode, String message) {
        super(exceptionCode, message);
    }

    public CustomExpection(Integer exceptionCode, String message, Throwable cause) {
        super(exceptionCode, message, cause);
    }
}
