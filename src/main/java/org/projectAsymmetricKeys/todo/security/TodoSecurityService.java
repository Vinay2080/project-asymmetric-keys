package org.projectAsymmetricKeys.todo.security;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.projectAsymmetricKeys.todo.Todo;
import org.projectAsymmetricKeys.todo.TodoRepository;
import org.projectAsymmetricKeys.user.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor

public class TodoSecurityService {

    private final TodoRepository todoRepository;

    public boolean isTodoOwner(final String todoId) {
        final Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        final String userID = ((User) authentication.getPrincipal()).getID();

        final Todo todo = this.todoRepository.findById(todoId)
                .orElseThrow(() -> new EntityNotFoundException("Todo is not found with id: " + todoId));

        return todo.getUser()
                .getID()
                .equals(userID);
    }
}
