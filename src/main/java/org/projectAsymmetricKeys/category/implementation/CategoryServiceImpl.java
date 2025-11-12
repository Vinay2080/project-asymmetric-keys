package org.projectAsymmetricKeys.category.implementation;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.projectAsymmetricKeys.category.Category;
import org.projectAsymmetricKeys.category.CategoryRepository;
import org.projectAsymmetricKeys.category.CategoryService;
import org.projectAsymmetricKeys.category.request.CreateCategoryRequest;
import org.projectAsymmetricKeys.category.request.UpdateCategoryRequest;
import org.projectAsymmetricKeys.category.response.CategoryResponse;
import org.projectAsymmetricKeys.exception.BusinessException;
import org.projectAsymmetricKeys.exception.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public String createCategory(final CreateCategoryRequest request, final String userID) {
        checkCategoryUnicityForUser(request.getName(), userID);
        final Category category = categoryMapper.toCategory(request);
        return categoryRepository
                .save(category)
                .getID();
    }


    @Override
    public void updateCategory(final UpdateCategoryRequest request, final String userID, final String catID) {
        final Category categoryToUpdate = categoryRepository.findById(catID).orElseThrow(() -> new EntityNotFoundException("no category found with id: " + catID));
        checkCategoryUnicityForUser(request.getName(), userID);
        categoryMapper.mergeCategory(categoryToUpdate, request);
        categoryRepository.save(categoryToUpdate);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryResponse> findAllByOwner(final String userID) {
        return categoryRepository.findAllByUserID(userID)
                .stream()
                .map(categoryMapper::toCategoryResponse)
                .toList();
    }

    @Override
    public CategoryResponse findCategoryByID(final String catID) {
        return this.categoryRepository.findById(catID)
                .map(categoryMapper::toCategoryResponse)
                .orElseThrow(() -> new EntityNotFoundException("no category found with id" + catID));
    }

    @Override
    public void deleteCategoryByID(final String catID) {
        // todo
        // mark the category for deletion
        // the scheduler should pick up all the marked categories and perform the deletion

    }

    private void checkCategoryUnicityForUser(final String name, final String userID) {
        final boolean alreadyExistsForUser = categoryRepository.findByNameAndUserID(name, userID);
        if (alreadyExistsForUser) {
            throw new BusinessException(ErrorCode.CATEGORY_ALREADY_EXISTS_FOR_USER);
        }
    }
}
