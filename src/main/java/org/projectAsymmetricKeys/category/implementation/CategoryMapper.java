package org.projectAsymmetricKeys.category.implementation;

import org.apache.commons.lang3.StringUtils;
import org.projectAsymmetricKeys.category.Category;
import org.projectAsymmetricKeys.category.request.CreateCategoryRequest;
import org.projectAsymmetricKeys.category.request.UpdateCategoryRequest;
import org.projectAsymmetricKeys.category.response.CategoryResponse;
import org.springframework.stereotype.Service;

@Service
public class CategoryMapper {
    public Category toCategory(final CreateCategoryRequest request) {
        return Category
                .builder()
                .name(request.getName())
                .description(request.getDescription())
                .build();
    }

    public void mergeCategory(final Category categoryToUpdate, final UpdateCategoryRequest request) {

        if (StringUtils.isNotBlank(request.getName())
                && !categoryToUpdate.getName().equals(request.getName())) {
            categoryToUpdate.setName(request.getName());
        }
        if (StringUtils.isNotBlank(request.getDescription())
                && !categoryToUpdate.getDescription().equals(request.getDescription())) {
            categoryToUpdate.setDescription(request.getDescription());
        }
    }

    public CategoryResponse toCategoryResponse(final Category category) {
        return CategoryResponse
                .builder()
                .name(category.getName())
                .description(category.getDescription())
                .todoCount(category.getTodos()
                        .size())
                .build();
    }
}
