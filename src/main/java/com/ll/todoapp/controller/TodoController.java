package com.ll.todoapp.controller;

import com.ll.todoapp.dto.TodoDto;
import com.ll.todoapp.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@RequestMapping("/todos")
@RequiredArgsConstructor
@Controller
public class TodoController {

    private final TodoService todoService;

    @GetMapping("")
    public String todos(Model model) {
        // 이전에 만들었던 레포지터리와 다른 객체를 사용하면 안됨
        // TodoRepository todoRepository = new TodoRepository();
        List<TodoDto> todos = todoService.getAllTodos();
        model.addAttribute("todos", todos);
        model.addAttribute("totalCount", todoService.getTotalCount());
        model.addAttribute("completedCount", todoService.getCompletedCount());
        model.addAttribute("activeCount", todoService.getActiveCount());
        return "todos";
    }

    @GetMapping("/new")
    public String newTodo(Model model) {
        model.addAttribute("todo", new TodoDto());
        return "form";
    }

    @PostMapping("/create")
    public String create(RedirectAttributes redirectAttributes, @ModelAttribute TodoDto todo) {
        TodoDto todoDto = new TodoDto(null, todo.getTitle(), todo.getContent(), false);
        todoService.createTodo(todoDto);
        redirectAttributes.addFlashAttribute("message", "메모가 생성되었습니다.");
        return "redirect:/todos";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        TodoDto todo = todoService.getTodoById(id);
        model.addAttribute("todo", todo);

        return "detail";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        todoService.deleteTodoById(id);

        return "redirect:/todos";
    }

    @GetMapping("/{id}/update")
    public String edit(@PathVariable Long id, Model model) {
        TodoDto todo = todoService.getTodoById(id);
        model.addAttribute("todo", todo);

        return "form";
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable Long id, @ModelAttribute TodoDto todo) {
        todo.setId(id);
        todoService.updateTodoById(id, todo);
        return "redirect:/todos/" + id;
    }

    @GetMapping("/search")
    public String search(@RequestParam String keyword, Model model) {
        List<TodoDto> todos = todoService.searchTodos(keyword);
        model.addAttribute("todos", todos);
        return "todos";
    }

    @GetMapping("/active")
    public String active(Model model) {
        List<TodoDto> todos = todoService.getTodosByCompleted(false);
        model.addAttribute("todos", todos);
        model.addAttribute("totalCount", todoService.getTotalCount());
        model.addAttribute("completedCount", todoService.getCompletedCount());
        model.addAttribute("activeCount", todoService.getActiveCount());
        return "todos";
    }

    @GetMapping("/completed")
    public String completed(Model model) {
        List<TodoDto> todos = todoService.getTodosByCompleted(true);
        model.addAttribute("todos", todos);
        model.addAttribute("totalCount", todoService.getTotalCount());
        model.addAttribute("completedCount", todoService.getCompletedCount());
        model.addAttribute("activeCount", todoService.getActiveCount());
        return "todos";
    }

    @GetMapping("/todos/{id}/toggle")
    public String toggle(@PathVariable Long id) {
        TodoDto todo = todoService.getTodoById(id);
        todo.setCompleted(!todo.isCompleted());
        todoService.createTodo(todo);
        return "redirect:/todos/" + id;
    }

    // 제목 검증 추가

    @GetMapping("/delete-completed")
    public String deleteCompleted(RedirectAttributes redirectAttributes) {
        todoService.deleteCompleted();
        redirectAttributes.addFlashAttribute("message", "완료된 할일 삭제 되었습니다.");
        return "redirect:/todos";
    }
}


