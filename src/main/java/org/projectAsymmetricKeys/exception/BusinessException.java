package org.projectAsymmetricKeys.exception;

public class BusinessException extends RuntimeException {

    public BusinessException(ErrorCode errorCode, Object args){
        super(getFormatterMessage(errorCode, args));
    }

    public BusinessException(ErrorCode errorCode){
        super(getFormatterMessage(errorCode));
    }

    private static String getFormatterMessage(ErrorCode errorCode) {
        return errorCode.getDefaultMessage();
    }
    private static String getFormatterMessage(ErrorCode errorCode, Object args) {
            return String.format(errorCode.getDefaultMessage(), args);
    }

}
// todo pass arguments from where the exception is called when required