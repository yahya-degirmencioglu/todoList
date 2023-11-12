package de.yahya.todolist.repository;


import de.yahya.todolist.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {

    @Query("SELECT t FROM Task t WHERE lower(t.description) LIKE %?1%")
    public List<Task> search(String keyword);

    @Query("SELECT t FROM Task t  where t.user.id = ?1")
    public List<Task>  findByAllWithUserId(Long id);


}
