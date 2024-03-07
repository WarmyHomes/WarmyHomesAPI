package com.project.repository.user;

import com.project.entity.business.Advert;
import com.project.entity.business.Tour_Request;
import com.project.entity.enums.RoleType;
import com.project.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    void findByUsernameEquals(String username);

    User findByEmail(String email);

    boolean existsByEmail(String email);

    User findByEmailEquals(String email);

    List<Tour_Request> findByTourRequestList(Long id);

    @Query("SELECT COUNT (u) FROM User u INNER JOIN u.userRole r WHERE r.roleType =?1")
    long countAdmin(RoleType roleType);

    List<Advert> findByAdvertList(Long id);

    List<Tour_Request> findByTourRequest(Long id);
}
