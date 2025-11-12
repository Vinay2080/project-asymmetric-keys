package org.projectAsymmetricKeys.todo.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.projectAsymmetricKeys.category.Category;
import org.projectAsymmetricKeys.category.CategoryRepository;
import org.projectAsymmetricKeys.todo.Todo;
import org.projectAsymmetricKeys.todo.TodoMapper;
import org.projectAsymmetricKeys.todo.TodoRepository;
import org.projectAsymmetricKeys.todo.TodoService;
import org.projectAsymmetricKeys.todo.request.TodoRequest;
import org.projectAsymmetricKeys.todo.request.TodoUpdateRequest;
import org.projectAsymmetricKeys.todo.response.TodoResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;
    private final TodoMapper todoMapper;
    private CategoryRepository categoryRepository;

    @Override
    public String createTodo(TodoRequest request, String userID) {
        final Category category = validateCategory(request.getCategoryID(), userID);
        final Todo todo = todoMapper.toTodo(request);
        todo.setCategory(category);
        return todoRepository.save(todo).getID();
    }

    @Override
    public void updateTodo(TodoUpdateRequest request, String todoID, String userID) {
        final Todo todoToUpdate = todoRepository
                .findById(todoID)
                .orElseThrow(() -> new EntityNotFoundException("Todo not found with: " + todoID));
        final Category category = validateCategory(request.getCategoryID(), userID);
        todoMapper.mergeTodo(todoToUpdate, request);
        todoToUpdate.setCategory(category);
        todoRepository.save(todoToUpdate);
    }

    @Override
    public TodoResponse findTodoByID(String todoID) {
        return todoRepository.findById(todoID)
                .map(todoMapper::toTodoResponse)
                .orElseThrow(() -> new EntityNotFoundException("no todo found with id " + todoID));
    }

    @Override
    public List<TodoResponse> findAllTodosForToday(String userID) {
        return this.todoRepository.findAllByUserID(userID)
                .stream()
                .map(todoMapper::toTodoResponse)
                .toList();
    }

    @Override
    public List<TodoResponse> findAllTodosByCategory(String catID, String userID) {
        return this.todoRepository.findAllByUserIDAndCategoryID(userID, catID)
                .stream()
                .map(todoMapper::toTodoResponse)
                .toList();
    }

    @Override
    public List<TodoResponse> findAllDueTodos(String userID) {
        return this.todoRepository.findAllDueTodos(userID)
                .stream()
                .map(todoMapper::toTodoResponse)
                .toList();
    }

    @Override
    public void deleteTodosByID(String todoID) {
        todoRepository.deleteById(todoID);

    }

    private Category validateCategory(final String categoryID, final String userID) {
        return categoryRepository.findByIdAndUserID(categoryID, userID)
                .orElseThrow(() -> new EntityNotFoundException("no category was found for that user ID: " + categoryID));
    }
}
