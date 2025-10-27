package org.projectAsymmetricKeys.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    USER_NOT_FOUND("USER_NOT_FOUND", "user %s not found", HttpStatus.NOT_FOUND),

    CONFIRM_PASSWORD_MISMATCH("CONFIRM_PASSWORD_MISMATCH", "new password does not match with confirm password", HttpStatus.BAD_REQUEST),
    CURRENT_PASSWORD_MISMATCH("CURRENT_PASSWORD_MISMATCH", "password does not match with current password", HttpStatus.FORBIDDEN),
    NO_STATUS_CHANGE("NO_STATUS_CHANGE", "account is already in requested state (enabled/ disabled)", HttpStatus.CONFLICT),
    // jwt error responses
    INVALID_JWT_TOKEN("INVALID_JWT_TOKEN", "invalid jwt token = %s", HttpStatus.UNAUTHORIZED),
    INVALID_JWT_TOKEN_TYPE("INVALID_JWT_TOKEN_TYPE", "invalid jwt token type expected 'REFRESH_TOKEN' got %s ", HttpStatus.UNAUTHORIZED),
    JWT_TOKEN_EXPIRED("REFRESH TOKEN EXPIRED", "token expired on date = %s", HttpStatus.UNAUTHORIZED),

    EMAIL_ALREADY_EXISTS("EMAIL_ALREADY_EXISTS", "email already in use, chose different email", HttpStatus.CONFLICT),
    PHONE_ALREADY_EXISTS("PHONE_NUMBER_ALREADY_EXISTS", "phone number already in use, chose different phone number", HttpStatus.CONFLICT),

    ERROR_USER_DISABLED("ERROR_USER_DISABLED", "User account is disabled, please activate your account or contact the administrator", HttpStatus.FORBIDDEN),
    BAD_CREDENTIALS("BAD_CREDENTIALS", "username or/and password is incorrect", HttpStatus.UNAUTHORIZED),
    USERNAME_NOT_FOUND("USER_NOT_FOUND", "cannot find user with provided username %s", HttpStatus.NOT_FOUND),
    INTERNAL_EXCEPTION("INTERNAL_EXCEPTION", "an internal error occurred, please try again later or contact the admin", HttpStatus.INTERNAL_SERVER_ERROR);

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
