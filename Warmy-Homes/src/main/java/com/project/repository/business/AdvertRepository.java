package com.project.repository.business;

import com.project.entity.business.Advert;
import com.project.entity.business.Category;
import com.project.entity.business.City;
import com.project.entity.business.helperentity.Advert_Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    //bilgichoca
    boolean existsByAdvert_Type_Id(Long advertTypeId);

  @Query("SELECT e FROM Entity e WHERE LOWER(e.title) LIKE LOWER(CONCAT('%', :q, '%')) OR LOWER(e.description) LIKE LOWER(CONCAT('%', :q, '%'))")
    Page<Advert> findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String q, String q1, Pageable pageable);

  @Query("SELECT a FROM Advert a " +
          "WHERE a.categoryId = :categoryId " +
          "AND a.advertTypeId = :advertTypeId " +
          "AND a.price BETWEEN :priceStart AND :priceEnd " +
          "AND a.status = :status " +
          "ORDER BY CASE WHEN :sort = 'price' AND :type = 'asc' THEN a.price END ASC, " +
          "CASE WHEN :sort = 'price' AND :type = 'desc' THEN a.price END DESC, " +
          "CASE WHEN :sort = 'status' AND :type = 'asc' THEN a.status END ASC, " +
          "CASE WHEN :sort = 'status' AND :type = 'desc' THEN a.status END DESC")
  Page<Advert> findAllByCategoryIdAndAdvertTypeIdAndPriceBetweenAndStatusOrderBy(Pageable pageable, Long categoryId, Long advertTypeId, Double priceStart, Double priceEnd, Integer status, String sort, String type);
}
