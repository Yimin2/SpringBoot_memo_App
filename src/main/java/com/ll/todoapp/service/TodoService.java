package com.ll.todoapp.service;

import com.ll.todoapp.dto.TodoDto;
import com.ll.todoapp.entity.TodoEntity;
import com.ll.todoapp.exception.ResourceNotFoundException;
import com.ll.todoapp.repogitory.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;

    public List<TodoDto> getAllTodos() {
        return todoRepository.findAll()
                .stream()
                .map(TodoDto::from)
                .collect(Collectors.toList());
    }

    private TodoEntity findEntityById(Long id) {
        return todoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("not found : id" + id));
    }

    public TodoDto getTodoById(Long id) {
        TodoEntity entity = findEntityById(id);
        return TodoDto.from(entity);
    }

    public TodoDto createTodo(TodoDto dto) {
        validateTitle(dto.getTitle());
        TodoEntity entity = TodoEntity.from(dto);
        TodoEntity saved = todoRepository.save(entity);

        return TodoDto.from(saved);
    }

    public void deleteTodoById(Long id) {
        getTodoById(id);
        todoRepository.deleteById(id);
    }


    public TodoDto updateTodoById(Long id, TodoDto todoDto) {
        validateTitle(todoDto.getTitle());
        TodoEntity todoEntity = findEntityById(id);

        TodoEntity updatedEntity = todoEntity.toBuilder()
                .title(todoDto.getTitle())
                .content(todoDto.getContent())
                .completed(todoDto.isCompleted())
                .build();

        return TodoDto.from(todoRepository.save(updatedEntity));
    }

    public List<TodoDto> searchTodos(String keyword) {
        return todoRepository.findByTitleContaining(keyword)
                .stream()
                .map(TodoDto::from)
                .collect(Collectors.toList());
    }

    public List<TodoDto> getTodosByCompleted(boolean completed) {
        return todoRepository.findByCompleted(completed).stream().map(TodoDto::from).collect(Collectors.toList());
    }

    public long getTotalCount() {
        return todoRepository.findAll()
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
        todoRepository.deleteByCompleted(true);
    }
}
