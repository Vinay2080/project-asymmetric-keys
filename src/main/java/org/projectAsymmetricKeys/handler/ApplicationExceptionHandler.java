package org.projectAsymmetricKeys.handler;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.projectAsymmetricKeys.exception.BusinessException;
import org.projectAsymmetricKeys.exception.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class ApplicationExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleException(final BusinessException exception) {
        final ErrorResponse body = ErrorResponse
                .builder()
                .code(
                        exception.getErrorCode()
                                .getCode())
                .message(exception.getMessage())
                .build();
        log.info("Business Exception {}", body);
        log.debug(exception.getMessage(), exception);

        return ResponseEntity.
                status(
                        exception
                                .getErrorCode()
                                .getHttpStatus() != null ?
                                exception
                                        .getErrorCode()
                                        .getHttpStatus() : HttpStatus.BAD_REQUEST)
                .body(body);
    }

    // different ways to return responseEntity
    // without using new keyword
    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<ErrorResponse> handleException() {
        final ErrorResponse body = ErrorResponse
                .builder()
                .code(ErrorCode.ERROR_USER_DISABLED.getCode())
                .message(ErrorCode.ERROR_USER_DISABLED.getDefaultMessage())
                .build();
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(body);
    }

    // with using new keyword
    // without using new keyword is better
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleException(final MethodArgumentNotValidException validException) {
        final List<ErrorResponse.ValidationError> validationErrorList = new ArrayList<>();
        validException
                .getBindingResult()
                .getAllErrors()
                .forEach(
                        error -> {
                            final String fieldName = ((FieldError) error).getField();
                            final String errorCode = error.getDefaultMessage();
                            validationErrorList
                                    .add(ErrorResponse
                                            .ValidationError
                                            .builder()
                                            .field(fieldName)
                                            .code(errorCode)
                                            .message(errorCode)
                                            .build()
                                    );
                        }
                );
        final ErrorResponse errorResponse = ErrorResponse
                .builder()
                .validationErrorList(validationErrorList)
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleException(final BadCredentialsException exception){
        log.debug(exception.getMessage(), exception);
        final ErrorResponse errorResponse = ErrorResponse
                .builder()
                .code(ErrorCode.BAD_CREDENTIALS.getCode())
                .message(ErrorCode.BAD_CREDENTIALS.getDefaultMessage())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleException(final EntityNotFoundException exception){
        log.debug(exception.getMessage(), exception);
        final ErrorResponse errorResponse = ErrorResponse
                .builder()
                .code("TBD")
                .message(exception.getMessage())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
