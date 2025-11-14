package org.projectAsymmetricKeys.category.security;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.projectAsymmetricKeys.category.Category;
import org.projectAsymmetricKeys.category.CategoryRepository;
import org.projectAsymmetricKeys.user.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategorySecurityService {

    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public boolean isCategoryOwner(final String categoryID) {
        final Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();
        final String userID = ((User) authentication.getPrincipal()).getID();
        final Category category = categoryRepository.findById(categoryID)
                .orElseThrow(() -> new RuntimeException("category not found"));

        return category.getCreatedBy().equals(userID);
    }
}
