package com.project.repository.business;

import com.project.entity.business.Advert;
import com.project.entity.business.Category;
import com.project.entity.business.City;
import com.project.entity.business.helperentity.Advert_Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import java.time.LocalDate;
import java.lang.reflect.Array;

import java.util.List;

public interface AdvertRepository extends JpaRepository<Advert, Long> {

    boolean existsAdvertBySlug(String slug);


  // Use Method For Report
    List<Advert> findAdvertsByFilter(LocalDate beginningDate,
                                     LocalDate endingDate,
                                     Category category,
                                     Advert_Type advertType);

    @Query("SELECT a FROM Advert a WHERE a.slug= ?1")
    Advert findBySlugContaining(String slug);
}
