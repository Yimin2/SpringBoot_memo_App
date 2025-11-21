package com.ll.todoapp.dto;

import com.ll.todoapp.entity.TodoEntity;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TodoDto {
    private Long id;

    private String title;

    private String content;
    private boolean completed;

    public static TodoDto from(TodoEntity entity) {
        TodoDto dto = new TodoDto();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setContent(entity.getContent());
        dto.setCompleted(entity.isCompleted());

        return dto;
    }
}
