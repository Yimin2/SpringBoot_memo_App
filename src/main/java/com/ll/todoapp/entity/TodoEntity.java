package com.ll.todoapp.entity;

import com.ll.todoapp.dto.TodoDto;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(toBuilder = true)
@Table(name = "todos")
public class TodoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String title;

    @Column(nullable = false, length = 500)
    private String content;

    private boolean completed;

    public void update(String title, String content, boolean completed) {
        this.title = title;
        this.content = content;
        this.completed = completed;
    }

    public static TodoEntity from(TodoDto dto) {
        return TodoEntity.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .completed(dto.isCompleted())
                .build();
    }
}
