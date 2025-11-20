package com.ll.todoapp.repogitory;

import com.ll.todoapp.dto.TodoDto;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class TodoRepository {
    private final Map<Long, TodoDto> storage = new ConcurrentHashMap<>();
    private Long nextId = 1L;

    public TodoDto save(TodoDto todo) {
        if (todo.getId() == null) {
            todo.setId(nextId++);
        }

        storage.put(todo.getId(), todo);

        return todo;
    }

    public List<TodoDto> finAll() {
        return new ArrayList<>(storage.values());
    }

    public Optional<TodoDto> findById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    public void deleteById(Long id) {
        storage.remove(id);
    }

    public List<TodoDto> findByTitleContaining(String keyword) {
        return storage.values()
                .stream()
                .filter((todoDto -> todoDto.getTitle()
                        .contains(keyword)))
                .toList();
    }

    public List<TodoDto> findByCompleted(boolean completed) {
        return storage.values()
                .stream()
                .filter(todoDto -> todoDto.isCompleted() == completed)
                .toList();
    }

    public void deleteByCompletedTodos() {
        storage.entrySet().removeIf(
                item -> item.getValue().isCompleted()
        );
    }
}