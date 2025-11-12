package org.projectAsymmetricKeys.todo;

import org.projectAsymmetricKeys.todo.request.TodoRequest;
import org.projectAsymmetricKeys.todo.request.TodoUpdateRequest;
import org.projectAsymmetricKeys.todo.response.TodoResponse;

import java.util.List;

public interface TodoService {

    String createTodo(TodoRequest request, String userID);

    void updateTodo(TodoUpdateRequest request, String todoID, String userID);

    TodoResponse findTodoByID(String todoID);

    List<TodoResponse> findAllTodosForToday(String userID);

    List<TodoResponse> findAllTodosByCategory(String catID, String userID);

    List<TodoResponse> findAllDueTodos(String userID);

    void deleteTodosByID(String todoID);

}
