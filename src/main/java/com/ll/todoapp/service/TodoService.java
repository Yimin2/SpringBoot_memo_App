package com.ll.todoapp.service;

import com.ll.todoapp.dto.TodoDto;
import com.ll.todoapp.repogitory.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;

    public List<TodoDto> getAllTodos() {
        return todoRepository.finAll();
    }

    public TodoDto getTodoById(Long id) {
        return todoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found : id" + id));
    }

    public void deleteTodoById(Long id) {
        getTodoById(id);
        todoRepository.deleteById(id);
    }

    public TodoDto saveTodo(TodoDto todoDto) {
        validateTitle(todoDto.getTitle());
        return todoRepository.save(todoDto);
    }

    public TodoDto updateTodoById(Long id, TodoDto newTodo) {
        validateTitle(newTodo.getTitle());
        TodoDto originTodo = getTodoById(id);

        originTodo.setTitle(newTodo.getTitle());
        originTodo.setContent(newTodo.getContent());
        originTodo.setCompleted(newTodo.isCompleted());

        return todoRepository.save(originTodo);
    }

    public List<TodoDto> searchTodos(String keyword) {
        return todoRepository.findByTitleContaining(keyword);
    }

    public List<TodoDto> getTodosByCompleted(boolean completed) {
        return todoRepository.findByCompleted(completed);
    }

    public long getTotalCount() {
        return todoRepository.finAll()
                .size();
    }

    public long getCompletedCount() {
        return todoRepository.findByCompleted(true)
                .size();
    }

    public long getActiveCount() {
        return todoRepository.findByCompleted(false)
                .size();
    }

    private void validateTitle(String title) {
        if (title == null || title.trim()
                .isEmpty()) {
            throw new IllegalArgumentException("제목 필수");
        }
        if (title.length() > 50) {
            throw new IllegalArgumentException("길이 제한");
        }
    }

    public void deleteCompleted() {
        todoRepository.deleteByCompletedTodos();
    }
}
