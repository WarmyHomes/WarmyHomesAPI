package com.project.repository.business;

import com.project.entity.business.Advert;
import com.project.entity.business.Category;
import com.project.entity.business.City;
import com.project.entity.business.helperentity.Advert_Type;
import com.project.entity.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import java.time.LocalDate;
import java.lang.reflect.Array;

import java.util.List;
import java.util.Optional;

public interface AdvertRepository extends JpaRepository<Advert, Long> {

    boolean existsAdvertBySlug(String slug);



    @Query("SELECT a FROM Advert a WHERE a.createdAt = :beginningDate " +
            "AND a.updated_at = :endingDate AND a.category = :category " +
            "AND a.advert_type = :advertType")
    List<Advert> findAdvertsByFilter(@Param("beginningDate") LocalDate beginningDate,
                                     @Param("endingDate") LocalDate endingDate,
                                     @Param("category") Category category,
                                     @Param("advertType") Advert_Type advertType);



    @Query("SELECT a FROM Advert a WHERE a.slug = :slug")
    Advert findBySlugContaining(String slug);



//  @Query("SELECT e FROM Advert e WHERE LOWER(e.title) LIKE LOWER(CONCAT('%', :q, '%')) OR LOWER(e.description) LIKE LOWER(CONCAT('%', :q, '%'))")
//  Page<Advert> findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(@Param("q") String title, @Param("q") String description, Pageable pageable);
//
//  @Query("SELECT a FROM Advert a " +
//          "WHERE a.category = :categoryId " +
//          "AND a.advert_type= :advertTypeId " +
//          "AND a.price BETWEEN :priceStart AND :priceEnd " +
//          "AND a.status = :status " +
//          "ORDER BY CASE WHEN :sort = 'price' AND :type = 'asc' THEN a.price END ASC, " +
//          "CASE WHEN :sort = 'price' AND :type = 'desc' THEN a.price END DESC, " +
//          "CASE WHEN :sort = 'status' AND :type = 'asc' THEN a.status END ASC, " +
//          "CASE WHEN :sort = 'status' AND :type = 'desc' THEN a.status END DESC")
//  Page<Advert> findAllByCategoryIdAndAdvertTypeIdAndPriceBetweenAndStatusOrderBy(Pageable pageable, Long categoryId, Long advertTypeId, Double priceStart, Double priceEnd, Integer status, String sort, String type);

    @Query("SELECT a FROM Advert a " +
            "WHERE (:q IS NULL OR LOWER(a.title) LIKE CONCAT('%', LOWER(:q), '%') OR LOWER(a.description) LIKE CONCAT('%', LOWER(:q), '%') ) " +
            "AND (:cId IS NULL OR a.category.id = :cId) " +
            "AND (:aId IS NULL OR a.advert_type.id = :aId) " +
            "AND (:pr1 IS NULL OR a.price >= :pr1) " +
            "AND (:pr2 IS NULL OR a.price <= :pr2)")
    Page<Advert> searchAllProducts(@Param("q") String q, @Param("cId") Long categoryId,
                                    @Param("aId") Long advertTypeId, @Param("pr1") Double priceStart,
                                    @Param("pr2") Double priceEnd, Pageable pageable);

  // NOT: This method wrote for Report.
  @Query("SELECT COUNT(id) FROM Advert ")
  Long countAllAdvert();

    @Query("SELECT COUNT(id) FROM Advert ")
    int countByAdvert(Advert advert);

    @Query("SELECT c FROM Category c WHERE c.slug = ?1")
    Optional<Advert> findBySlug(String slug);




    //Page<Advert> findByTitleOrDescriptionEquals(String title, Pageable pageable);

}
