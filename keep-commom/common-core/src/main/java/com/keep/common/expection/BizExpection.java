package com.keep.common.expection;

public class BizExpection extends RootRuntimeException{

    public BizExpection(String message) {
        super(message);
    }

    public BizExpection(Integer exceptionCode, String message) {
        super(exceptionCode, message);
    }

    public BizExpection(Integer exceptionCode, String message, Throwable cause) {
        super(exceptionCode, message, cause);
    }
}
