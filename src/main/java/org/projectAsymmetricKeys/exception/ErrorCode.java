package org.projectAsymmetricKeys.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    USER_NOT_FOUND("USER NOT FOUND", "user %s not found", HttpStatus.NOT_FOUND),

    CONFIRM_PASSWORD_MISMATCH("CONFIRM PASSWORD MISMATCH","new password does not match with confirm password" ,HttpStatus.BAD_REQUEST),
    CURRENT_PASSWORD_MISMATCH("CURRENT PASSWORD MISMATCH","password does not match with current password" ,HttpStatus.FORBIDDEN ),
    NO_STATUS_CHANGE("NO STATUS CHANGE","account is already in requested state (enabled/ disabled)" , HttpStatus.CONFLICT),
    // jwt error responses
    INVALID_JWT_TOKEN("INVALID JWT TOKEN","invalid jwt token = %s" ,HttpStatus.UNAUTHORIZED ),
    INVALID_JWT_TOKEN_TYPE("INVALID JWT TOKEN TYPE", "invalid jwt token type expected 'REFRESH_TOKEN' got %s ",HttpStatus.UNAUTHORIZED ),
    JWT_TOKEN_EXPIRED("REFRESH TOKEN EXPIRED","token expired on date = %s" , HttpStatus.UNAUTHORIZED),

    EMAIL_ALREADY_EXISTS("EMAIL ALREADY EXISTS","email already in use, chose different email" ,HttpStatus.CONFLICT ),
    PHONE_ALREADY_EXISTS("PHONE NUMBER ALREADY EXISTS", "phone number already in use, chose different phone number",HttpStatus.CONFLICT );

    private final String code;
    private final String defaultMessage;
    private final HttpStatus httpStatus;

    ErrorCode(
            final String code,
            final String defaultMessage,
            final HttpStatus httpStatus
    ) {
        this.code = code;
        this.defaultMessage = defaultMessage;
        this.httpStatus = httpStatus;
    }
}
