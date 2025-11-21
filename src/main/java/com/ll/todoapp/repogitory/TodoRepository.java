package com.ll.todoapp.repogitory;

import com.ll.todoapp.entity.TodoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoRepository extends JpaRepository<TodoEntity, Long> {
    List<TodoEntity> findByTitleContaining(String keyword);
    List<TodoEntity> findByCompleted(boolean completed);
    void deleteByCompleted(boolean completed);

}
