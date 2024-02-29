package com.project.repository.business;

import com.project.entity.business.Advert;
import com.project.entity.business.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;

import java.lang.reflect.Array;

public interface AdvertRepository extends JpaRepository<Advert, Long> {
    boolean existsAdvertById(Long id);

    boolean existsAdvertBySlug(String slug);

    City getCityByAdvert(String request);
}
