package org.projectAsymmetricKeys.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface CategoryRepository extends JpaRepository<Category, String> {


    @Query("""
            select count(c) > 0
            from Category c
            where lower(c.name) = lower(:name)
                and c.createdBy = :userID
               or c.createdBy = 'app'
            """)
    boolean findByNameAndUserID(String name, String userID);


    @Query("""
            select c
            from Category c
            where c.createdBy = :userID
               or c.createdBy = 'app'
            """
    )
    List<Category> findAllByUserID(String userID);

    @Query("""
            select c from Category c
            where c.ID = :categoryID
            and (c.createdBy = :userID or c.createdBy = 'app')
            """)
    Optional<Category> findByIdAndUserID(String categoryID, String userID);
}
