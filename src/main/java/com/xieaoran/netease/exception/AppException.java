package com.xieaoran.netease.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppException extends RuntimeException {
    private int errorCode;

    public AppException(int errorCode, String message) {
        super(message);
        this.setErrorCode(errorCode);
    }

    public AppException(int errorCode, String message, Throwable cause) {
        super(message, cause);
        this.setErrorCode(errorCode);
    }

    public AppException(ErrorCode errorCode) {
        this(-errorCode.ordinal(), errorCode.getMessage());
    }

    public AppException(ErrorCode errorCode, Throwable cause) {
        this(-errorCode.ordinal(), errorCode.getMessage(), cause);
    }
}
