package com.ll.todoapp.controller;

import com.ll.todoapp.dto.TodoDto;
import com.ll.todoapp.repogitory.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/create")
    public String create(@RequestParam String title, @RequestParam String content) {
        TodoDto todoDto = new TodoDto(null, title, content, false);
        todoRepository.save(todoDto);

        return "redirect:/todos";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        try {
            TodoDto todo = todoRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("not found"));
            model.addAttribute("todo", todo);

            return "detail";
        } catch (IllegalArgumentException e) {
            return "redirect:/todos";
        }
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        todoRepository.deleteById(id);

        return "redirect:/todos";
    }

    @GetMapping("/{id}/update")
    public String edit(@PathVariable Long id, Model model) {
        try {
            TodoDto todo = todoRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("not found"));
            model.addAttribute("todo", todo);

            return "edit";
        } catch (IllegalArgumentException e) {
            return "redirect:/todos";
        }
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable Long id, @RequestParam String title, @RequestParam String content, @RequestParam(defaultValue = "false") Boolean completed) {
        try {
            TodoDto todo = todoRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("not found"));

            todo.setTitle(title);
            todo.setContent(content);
            todo.setCompleted(completed);
            todoRepository.save(todo);

            return "redirect:/todos/" + id;
        } catch (IllegalArgumentException e) {
            return "redirect:/todos";
        }
    }

    @GetMapping("/search")
    public String search(@RequestParam String keyword, Model model) {
        List<TodoDto> todos = todoRepository.findByTitleContaining(keyword);
        model.addAttribute("todos", todos);
        return "todos";
    }

    @GetMapping("/active")
    public String active(Model model) {
        List<TodoDto> todos = todoRepository.findByCompleted(false);
        model.addAttribute("todos", todos);
        return "todos";
    }

    @GetMapping("/completed")
    public String completed(Model model) {
        List<TodoDto> todos = todoRepository.findByCompleted(true);
        model.addAttribute("todos", todos);
        return "todos";
    }

    @GetMapping("/todos/{id}/toggle")
    public String toggle(@PathVariable Long id, Model model) {
        try {
            TodoDto todo = todoRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("not found"));
            todo.setCompleted(!todo.isCompleted());
            todoRepository.save(todo);
            return "redirect:/todos/" + id;
        } catch (IllegalArgumentException e) {
            return "redirect:/todos";
        }
    }
}


