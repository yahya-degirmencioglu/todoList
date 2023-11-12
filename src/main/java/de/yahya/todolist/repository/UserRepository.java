package de.yahya.todolist.repository;

import de.yahya.todolist.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

//    Optional<User> findByEmail(String email);

    User findByEmail(String email);

}
