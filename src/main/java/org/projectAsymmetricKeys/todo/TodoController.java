package org.projectAsymmetricKeys.todo;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.projectAsymmetricKeys.category.RestResponse;
import org.projectAsymmetricKeys.todo.request.TodoRequest;
import org.projectAsymmetricKeys.todo.request.TodoUpdateRequest;
import org.projectAsymmetricKeys.todo.response.TodoResponse;
import org.projectAsymmetricKeys.user.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/todos")
@RequiredArgsConstructor
@Tag(name = "Todos", description = "Todo API")
public class TodoController {

    private final TodoService todoService;

    @PostMapping
    public ResponseEntity<RestResponse> createTodo(
            @RequestBody
            @Valid final TodoRequest request,
            final Authentication authentication
    ) {
        final String userID = ((User) authentication.getPrincipal()).getID();
        final String todoID = todoService.createTodo(request, userID);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new RestResponse(todoID));
    }

    @PutMapping("/{todo-ID}")
    @PreAuthorize("@todoSecurityService.isTodoOwner(#todoID)")
    public ResponseEntity<Void> updateTodo(
            @RequestBody
            @Valid final TodoUpdateRequest request,
            @PathVariable("todo-ID") final String todoID,
            final Authentication authentication
    ) {
        final String userID = ((User) authentication.getPrincipal()).getID();
        todoService.updateTodo(request, todoID, userID);
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/{todo-ID}")
    @PreAuthorize("@todoSecurityService.isTodoOwner(#todoID)")
    public ResponseEntity<TodoResponse> findTodoByID(
            @PathVariable("todo-ID") final String todoID
    ) {
        return ResponseEntity.ok(todoService.findTodoByID(todoID));
    }

    @GetMapping("/today")
    public ResponseEntity<List<TodoResponse>> findAllTodosByUserID(
            final Authentication authentication
    ) {
        final String userID = ((User) authentication.getPrincipal()).getID();

        return ResponseEntity.ok(todoService.findAllTodosForToday(userID));
    }

    @GetMapping("/category/{category-ID}")
    @PreAuthorize("@categorySecurityService.isCategoryOwner(#categoryID)")
    public ResponseEntity<List<TodoResponse>> findAllTodosByCategory(
            @PathVariable("category-ID") final String categoryID,
            final Authentication authentication
    ) {
        final String userID = ((User) authentication.getPrincipal()).getID();

        return ResponseEntity.ok(todoService.findAllTodosByCategory(categoryID, userID));
    }

    @GetMapping("/due")
    public ResponseEntity<List<TodoResponse>> findAllDueTodos(
            final Authentication authentication
    ) {
        final String userID = ((User) authentication.getPrincipal()).getID();
        return ResponseEntity.ok(this.todoService.findAllDueTodos(userID));
    }

    @DeleteMapping("/{todo-ID}")
    @PreAuthorize("@todoSecurityService.isTodoOwner(#todoID)")
    public ResponseEntity<Void> deleteTodoByID(
            @PathVariable("todo-ID") final String todoID
    ) {
        this.todoService.deleteTodosByID(todoID);
        return ResponseEntity.ok()
                .build();
    }
}
