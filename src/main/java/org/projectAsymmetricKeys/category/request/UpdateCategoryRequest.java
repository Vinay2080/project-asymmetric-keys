package org.projectAsymmetricKeys.category.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateCategoryRequest {
    @NotBlank(message = " VALIDATION.CATEGORY.NAME.NOT_BLANK")
    private String name;
    @NotBlank(message = "VALIDATION.CATEGORY.DESCRIPTION.NOT_BLANK")
    private String description;
}