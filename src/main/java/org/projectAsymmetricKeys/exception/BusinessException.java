package org.projectAsymmetricKeys.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

    private final ErrorCode errorCode;
    private final Object args;
    public BusinessException(ErrorCode errorCode, Object args){
        super(getFormatterMessage(errorCode, args));
        this.errorCode = errorCode;
        this.args = args;
    }

    public BusinessException(ErrorCode errorCode){
        super(getFormatterMessage(errorCode));
        this.errorCode = errorCode;
        this.args = null;
    }

    private static String getFormatterMessage(ErrorCode errorCode) {
        return errorCode.getDefaultMessage();
    }
    private static String getFormatterMessage(ErrorCode errorCode, Object args) {
            return String.format(errorCode.getDefaultMessage(), args);
    }

}
// todo pass arguments from where the exception is called when required