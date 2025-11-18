package com.ll.todoapp.controller;

import com.ll.todoapp.dto.TodoDto;
import com.ll.todoapp.repogitory.TodoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/todos")
@Controller
public class TodoController {

    @GetMapping("")
    public String todos() {
        return "todos";
    }

    @GetMapping("/new")
    public String newTodo() {
        return "new";
    }

    @GetMapping("/create")
    public String create(@RequestParam String title, @RequestParam String content, Model model) {
        TodoDto todoDto = new TodoDto(null, title, content, false);
        TodoRepository todoRepository = new TodoRepository();

        TodoDto todo = todoRepository.save(todoDto);
        model.addAttribute("todo",todo);

        return "create";
    }

}
