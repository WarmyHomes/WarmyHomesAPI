package com.project.repository.business;

import com.project.entity.business.Advert;
import com.project.entity.business.Tour_Request;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TourRequestRepository extends JpaRepository<Tour_Request,Long> {

    @Override
    boolean existsById(Long id);

    @Query(value = "SELECT COUNT(*) FROM t_tour_request WHERE advert = ?1", nativeQuery = true)
    int countByAdvert(Advert advert);

    @Query("SELECT tr FROM Tour_Request tr WHERE tr.owner_user.id = ?1")
    Page<Tour_Request> findAllByUserId(Long userId, Pageable pageable);

    @Query("SELECT tr FROM Tour_Request tr WHERE tr.owner_user.id = ?1")
    List<Tour_Request> findAllByUserIdList(Long userId);


    @Query("SELECT tr FROM Tour_Request tr")
    Page<Tour_Request> findAll(Pageable pageable);
    long countTour_RequestById(Long id);
}
