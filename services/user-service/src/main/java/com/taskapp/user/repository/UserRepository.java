package com.taskapp.user.repository;

import com.taskapp.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);


}

//@Query("SELECT u FROM User u JOIN FETCH u.tasks WHERE u.id = :userId")
//List<User> findUserWithTasks(@Param("userId") Long userId);