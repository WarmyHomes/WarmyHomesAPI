package com.project.repository.user;

import com.project.entity.business.Tour_Request;
import com.project.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    void findByUsernameEquals(String username);

    User findByEmail(String email);

    boolean existsByEmail(String email);

    User findByEmailEquals(String email);

    List<Tour_Request> findByTourRequestList(Long id);
}
