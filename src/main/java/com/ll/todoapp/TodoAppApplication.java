package com.ll.todoapp;

import com.ll.todoapp.dto.TodoDto;
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
            TodoDto todoDto1 = new TodoDto(null, "hi", "hello",false);
            TodoDto todoDto2 = new TodoDto(null, "hi2", "hello2",true);
            TodoDto todoDto3 = new TodoDto(null, "hi3", "hello3",true);
            todoRepository.save(todoDto1);
            todoRepository.save(todoDto2);
            todoRepository.save(todoDto3);
        };
    }

}
