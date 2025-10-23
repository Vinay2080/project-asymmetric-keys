package org.projectAsymmetricKeys.exception;

public class BusinessException extends RuntimeException {

    public BusinessException(ErrorCode errorCode, Object... args){
        super(getFormatterMessage(errorCode, args));
    }

    private static String getFormatterMessage(ErrorCode errorCode, Object[] args) {
        if (args != null && args.length > 0){
            return String.format(errorCode.getDefaultMessage(), args);
        }
        return errorCode.getDefaultMessage();
    }
}
