package com.ll.todoapp;

import com.ll.todoapp.dto.TodoDto;
import com.ll.todoapp.entity.TodoEntity;
import com.ll.todoapp.repogitory.TodoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TodoAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(TodoAppApplication.class, args);
    }

    @Bean
    public CommandLineRunner init(TodoRepository todoRepository) {
        return args -> {
            todoRepository.save(TodoEntity.builder()
                    .title("hi")
                    .content("hello")
                    .completed(false)
                    .build());
            todoRepository.save(TodoEntity.builder()
                    .title("hi1")
                    .content("hello2")
                    .completed(false)
                    .build());
            todoRepository.save(TodoEntity.builder()
                    .title("hi2")
                    .content("hello4")
                    .completed(false)
                    .build());
        };
    }

}
