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



    @Query("SELECT COUNT (u) FROM User u INNER JOIN u.userRoleList r WHERE r.roleType =?1")
    long countAdmin(RoleType roleType);



    List<Tour_Request> findByTourRequest(Long id);


    // NOT: This method wrote for Report.
    @Query("SELECT COUNT (id) FROM User ")
    Long countAllUser();
}
