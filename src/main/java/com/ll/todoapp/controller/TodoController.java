package com.ll.todoapp.controller;

import com.ll.todoapp.dto.TodoDto;
import com.ll.todoapp.repogitory.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("/todos")
@RequiredArgsConstructor
@Controller
public class TodoController {
    //    TodoRepository todoRepository = new TodoRepository();
    private final TodoRepository todoRepository;

    @GetMapping("")
    public String todos(Model model) {
        // 이전에 만들었던 레포지터리와 다른 객체를 사용하면 안됨
        // TodoRepository todoRepository = new TodoRepository();
        List<TodoDto> todos = todoRepository.finAll();
        model.addAttribute("todos", todos);
        return "todos";
    }

    @GetMapping("/new")
    public String newTodo() {
        return "new";
    }

    @GetMapping("/create")
    public String create(@RequestParam String title, @RequestParam String content, Model model) {
        TodoDto todoDto = new TodoDto(null, title, content, false);
        TodoDto todos = todoRepository.save(todoDto);
        model.addAttribute("todos", todos);

        return "create";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        TodoDto todo = todoRepository.findById(id);
        model.addAttribute("todo", todo);

        return "detail";
    }
}
