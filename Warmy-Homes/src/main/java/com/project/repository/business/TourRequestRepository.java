package com.project.repository.business;

import com.project.entity.business.Advert;
import com.project.entity.business.Tour_Request;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TourRequestRepository extends JpaRepository<Tour_Request,Long> {

    @Override
    boolean existsById(Long id);

    int countByAdvert(Advert advert);
}
