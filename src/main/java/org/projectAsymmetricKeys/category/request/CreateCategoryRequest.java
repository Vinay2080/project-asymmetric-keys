package org.projectAsymmetricKeys.category.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class CreateCategoryRequest implements Serializable {
    @NotBlank(message = "VALIDATION.CATEGORY.NAME.NOT_BLANK")
    String name;
    @NotBlank(message = "VALIDATION.CATEGORY.DESCRIPTION.NOT_BLANK")
    String description;
}