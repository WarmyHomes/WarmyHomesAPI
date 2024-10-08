package com.project.repository.business;

import com.project.entity.business.Advert;
import com.project.entity.business.Category;
import com.project.entity.business.City;
import com.project.entity.business.helperentity.Advert_Type;
import com.project.entity.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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


    @Query("SELECT a FROM Advert a WHERE (a.slug LIKE %:q% OR a.title LIKE %:q% OR a.location LIKE %:q%) " +
            "AND (:category_id IS NULL OR a.category.id = :category_id)"+
            "AND (:advert_type_id IS NULL OR a.advert_type.id = :advert_type_id)"+
            "AND (:price_start IS NULL OR a.price >= :price_start) " +
            "AND (:price_end IS NULL OR a.price <= :price_end)"+
            "AND (:city_id IS NULL OR a.city.id = :city_id)"
    )
    Page<Advert> searchAllProducts(@Param("q") String q, @Param("category_id") Long category_id,
                                   @Param("advert_type_id") Long advert_type_id,
                                   @Param("price_start") Double price_start,
                                   @Param("price_end") Double price_end,
                                   @Param("city_id") Long city_id,
                                   Pageable pageable);



    // NOT: This method wrote for Report.
  @Query("SELECT COUNT(id) FROM Advert ")
  Long countAllAdvert();

    @Query("SELECT COUNT(id) FROM Advert ")
    int countByAdvert(Advert advert);

    @Query("SELECT c FROM Category c WHERE c.slug = ?1")
    Optional<Advert> findBySlug(String slug);


    //G03
    // En popüler ilanları döndüren endpoint
    @Query("SELECT a FROM Advert a JOIN a.tourRequestList tr GROUP BY a.id ORDER BY COUNT(tr.id) DESC")
    List<Advert> findMostPopularProperties(Pageable pageable);


    //Page<Advert> findByTitleOrDescriptionEquals(String title, Pageable pageable);

}
