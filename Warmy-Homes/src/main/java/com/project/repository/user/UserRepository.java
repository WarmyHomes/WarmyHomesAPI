package com.project.repository.user;

import com.project.entity.business.Advert;
import com.project.entity.business.Tour_Request;
import com.project.entity.enums.RoleType;
import com.project.entity.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    //void findByUsernameEquals(String username);
    @Query("SELECT u FROM User u WHERE u.first_name LIKE %:q% OR u.last_name LIKE %:q% OR u.email LIKE %:q% OR u.phone LIKE %:q%")
    Page<User> findByUser(Pageable pageable, @Param("q") String q);

    @Query("SELECT u FROM User u WHERE u.email = :email")
    User findByEmail(@Param("email") String email);

    boolean existsByEmail(String email);

    User findByEmailEquals(String email);



   @Query("SELECT COUNT (u) FROM User u INNER JOIN u.userRole r WHERE r.roleType =?1")
    long countAdmin(RoleType roleType);

    @Query("SELECT COUNT (u) FROM User u INNER JOIN u.userRole r WHERE r.roleType =?1")
    long countManager(RoleType roleType);



   // List<Tour_Request> findByTourRequest(Long id);


    // NOT: This method wrote for Report.
    @Query("SELECT COUNT (id) FROM User ")
    Long countAllUser();

    @Query("SELECT tr FROM User tr WHERE  tr.tourRequests = ?1")
    Page<Tour_Request>findTourRequestByUserId(Long userid, Pageable pageable);

}
