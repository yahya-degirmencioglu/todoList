package de.yahya.todolist.controller;

import de.yahya.todolist.model.Task;
import de.yahya.todolist.model.TaskDto;
import de.yahya.todolist.services.LoginService;
import de.yahya.todolist.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private LoginService loginService;


    @GetMapping("")
    public String getAllTasks(String keyword, Model model) {

        if (loginService.isLoggedIn()) {
            model.addAttribute("title", "Tasks App");
            model.addAttribute("ac", "tasks");
            model.addAttribute("new", true);

            List<Task> tasks;
            if (keyword == null) {
//                tasks = taskService.findAll(); //alt version.
                  tasks = taskService.findByAllWithUserId(loginService.getUser().getId());
            } else {
                model.addAttribute("keyword", keyword);
                tasks = taskService.search(keyword);
            }
            List<TaskDto> newTasks = new TaskDto().convertorDateToString(tasks); // Date to String format
            model.addAttribute("todoList", newTasks);

            model.addAttribute("emailName", loginService.getUser().getEmail());
            if (tasks.isEmpty())
                model.addAttribute("em", true);
            else
                model.addAttribute("em", false);

            return "tasks";
        }
        return "login-form";
    }

    @GetMapping("new")
    public String newForm(Task task, Model model) {
        model.addAttribute("title", "Add new Task");
        return "task-form";
    }

    @PostMapping("add")
    public String newTask(@Valid Task task, BindingResult result, Model model) {
//        model.addAttribute("task", new Task());
        if (result.hasErrors()) {
            return "task-form";
        }
//        task.setId(task.getId());
        task.setDescription(task.getDescription());

        try {
            task.setCreatedDate(task.getCreatedDate()); //now
        } catch (NullPointerException e) {
            task.setCreatedDate(null);
        }

        task.setModifiedDate(null); //Default Wert ist null
        task.setComplete(false);

        task.setUser(loginService.getUser()); // ****

        taskService.save(task);

        return "redirect:/tasks";
    }


    @PostMapping("change")
    public String changeStatus(Integer id) {
        Task t = taskService.findById(id).get();
        if (t.getComplete()) {
            t.setComplete(false);
            t.setModifiedDate(null);
        } else {
            t.setComplete(true);
            t.setModifiedDate(LocalDateTime.now()); //modifierd time change
        }
        taskService.save(t);
        return "redirect:/tasks";
    }

    @PostMapping("delete")
    public String delete(Integer id) {
        Task task = taskService.findById(id).get();
        taskService.delete(task);
        return "redirect:/tasks";
    }

    @GetMapping("edit")
    public String editGetForm(Integer id, Model model) {
        model.addAttribute("headline", "Task Bearbeiten");
        model.addAttribute("ac", "tasks");
        model.addAttribute("task", taskService.findById(id).get());
        model.addAttribute("title", "Edit task");
        return "task-form";
    }


    @PostMapping("edit")
    public String editTask(Integer id, String description, Model model) {
        Task t = taskService.findById(id).get();
        t.setDescription(description);
        taskService.save(t);
        model.addAttribute("task", taskService.findById(id));
        return "tasks";

    }

    @GetMapping("json")
    public String jsonImport(Model model) {

//        List<Task> tasks = taskService.findAll();
        List<Task> tasks = taskService.findByAllWithUserId(loginService.getUser().getId());
        model.addAttribute("jsonTasks", tasks);

        return "json";
    }


}
