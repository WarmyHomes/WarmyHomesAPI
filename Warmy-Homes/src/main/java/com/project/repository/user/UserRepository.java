package com.project.repository.user;

import com.project.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    void findByUsernameEquals(String username);

    User findByEmail(String email);
}
