package org.projectAsymmetricKeys.category;

import org.projectAsymmetricKeys.category.request.CreateCategoryRequest;
import org.projectAsymmetricKeys.category.request.UpdateCategoryRequest;
import org.projectAsymmetricKeys.category.response.CategoryResponse;

import java.util.List;

public interface CategoryService {
    String createCategory(final CreateCategoryRequest request, final String userID);

    void updateCategory(UpdateCategoryRequest request, String userID, String catID);

    List<CategoryResponse> findAllByOwner(final String userID);

    CategoryResponse findCategoryByID(final String catID);

    void deleteCategoryByID(final String catID);
}
