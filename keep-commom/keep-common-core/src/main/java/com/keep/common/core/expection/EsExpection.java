package com.keep.common.core.expection;

public class EsExpection extends RootRuntimeException{

    public EsExpection(String message) {
        super(message);
    }

    public EsExpection(Integer exceptionCode, String message) {
        super(exceptionCode, message);
    }

    public EsExpection(Integer exceptionCode, String message, Throwable cause) {
        super(exceptionCode, message, cause);
    }
}
