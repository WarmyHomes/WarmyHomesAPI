package com.project.repository.business;

import com.project.entity.business.Advert;
import com.project.entity.business.Tour_Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TourRequestRepository extends JpaRepository<Tour_Request,Long> {

    @Override
    boolean existsById(Long id);

    @Query(value = "SELECT COUNT(*) FROM t_tour_request WHERE advert_id = ?1", nativeQuery = true)
    int countByAdvert(Advert advert);

    long countTour_RequestById(Long id);
}
