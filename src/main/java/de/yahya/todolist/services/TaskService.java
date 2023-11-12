package de.yahya.todolist.services;

import de.yahya.todolist.model.Task;
import de.yahya.todolist.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository repo;

    @Autowired
    public TaskService() {

    }

    public List<Task> findAll() {
        return repo.findAll();
    }

    public void save(Task task) {
        repo.save(task);
    }

    public void delete(Task task) {
        repo.delete(task);
    }

    public Optional<Task> findById(Integer id) {
        return repo.findById(id);

    }

    public List<Task> search(String keyword) {
        if (keyword != null) {
            return repo.search(keyword);
        }
        return repo.findAll();
    }

    public List<Task> findByAllWithUserId(Long id) {
        if (id != null) {
            return repo.findByAllWithUserId(id);
        }
        return repo.findAll();
    }
}
