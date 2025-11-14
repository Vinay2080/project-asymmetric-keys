package org.projectAsymmetricKeys.category;


import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.projectAsymmetricKeys.category.request.CreateCategoryRequest;
import org.projectAsymmetricKeys.category.request.UpdateCategoryRequest;
import org.projectAsymmetricKeys.category.response.CategoryResponse;
import org.projectAsymmetricKeys.user.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
@Tag(name = "Categories", description = "categories api")
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<RestResponse> createCategory(
            @RequestBody
            @Valid final CreateCategoryRequest request,
            final Authentication authentication
    ) {
        final String userID = ((User) authentication.getPrincipal()).getID();
        final String catID = categoryService.createCategory(request, userID);
        return ResponseEntity.status(HttpStatus.CREATED).body(new RestResponse(catID));
    }

    @PutMapping("/{category-id}")
    @PreAuthorize("@categorySecurityService.isCategoryOwner(#categoryID)")

    public ResponseEntity<Void> updateCategory(
            @RequestBody
            @Valid final UpdateCategoryRequest request,
            @PathVariable("category-id") final String categoryID,
            final Authentication authentication
    ) {
        final String userID = ((User) authentication.getPrincipal()).getID();
        categoryService.updateCategory(request, userID, categoryID);
        return ResponseEntity.accepted().build();
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> findAllCategories(
            @Valid final Authentication authentication
    ) {
        final String userID = ((User) authentication.getPrincipal()).getID();
        return ResponseEntity.ok(categoryService.findAllByOwner(userID));
    }

    @GetMapping("/{category-id}")
    @PreAuthorize("@categorySecurityService.isCategoryOwner(#categoryID)")

    public ResponseEntity<CategoryResponse> findCategoryByID(
            @PathVariable("category-id") final String categoryID
    ) {
        return ResponseEntity.ok(categoryService.findCategoryByID(categoryID));
    }

    @DeleteMapping("/{category-id}")
    @PreAuthorize("@categorySecurityService.isCategoryOwner(#categoryID)")
    public ResponseEntity<Void> deleteCategoryByID(
            @PathVariable("category-id") final String categoryID
    ) {
        categoryService.deleteCategoryByID(categoryID);
        return ResponseEntity.ok().build();

    }
}
