package org.projectAsymmetricKeys.handler;

import lombok.*;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ErrorResponse {
    String message;
    private String code;
    private List<ValidationError> validationErrorList;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class ValidationError {
        private String field;
        private String code;
        private String message;
    }
}
