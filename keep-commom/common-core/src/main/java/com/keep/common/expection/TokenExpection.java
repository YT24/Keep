package com.keep.common.expection;

public class TokenExpection extends RootRuntimeException{

    public TokenExpection(String message) {
        super(message);
    }

    public TokenExpection(Integer exceptionCode, String message) {
        super(exceptionCode, message);
    }

    public TokenExpection(Integer exceptionCode, String message, Throwable cause) {
        super(exceptionCode, message, cause);
    }
}
