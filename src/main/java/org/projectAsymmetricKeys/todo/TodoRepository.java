package org.projectAsymmetricKeys.todo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, String> {

    @Query("""
            select t from Todo t
            where t.user.ID = :userID
            and t.startDate = current_date
            """)
    List<Todo> findAllByUserID(String userID);

    List<Todo> findAllByUserIDAndCategoryID(String userID, String categoryID);

    @Query("""
            select t from Todo t
            where t.endDate > current_date and t.endTime >= current_time
            """)
    List<Todo> findAllDueTodos(String userID);
}
