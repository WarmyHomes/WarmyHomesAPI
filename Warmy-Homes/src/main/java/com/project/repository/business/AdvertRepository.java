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



    @Query("SELECT a FROM Advert a WHERE a.slug =: slug")
    Advert findBySlugContaining(String slug);



  @Query("SELECT e FROM Advert e WHERE LOWER(e.title) LIKE LOWER(CONCAT('%', :q, '%')) OR LOWER(e.description) LIKE LOWER(CONCAT('%', :q, '%'))")
  Page<Advert> findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(@Param("q") String title, @Param("q") String description, Pageable pageable);

    @Query("SELECT a FROM Advert a WHERE " +
            "a.category.id = :categoryId AND " +
            "a.advert_type.id = :advertTypeId AND " +
            "a.price BETWEEN :priceStart AND :priceEnd AND " +
            "a.status = :status")
    Page<Advert> findAllByCategoryIdAndAdvertTypeIdAndPriceBetweenAndStatusOrderBy(Pageable pageable,
                                                                                   @Param("categoryId") Long categoryId,
                                                                                   @Param("advertTypeId") Long advertTypeId,
                                                                                   @Param("priceStart") Double priceStart,
                                                                                   @Param("priceEnd") Double priceEnd,
                                                                                   @Param("status") Integer status);

  // NOT: This method wrote for Report.
  @Query("SELECT COUNT(id) FROM Advert ")
  Long countAllAdvert();

    @Query("SELECT COUNT(id) FROM Advert ")
    int countByAdvert(Advert advert);

    @Query("SELECT c FROM Category c WHERE c.slug = ?1")
    Optional<Advert> findBySlug(String slug);




    //Page<Advert> findByTitleOrDescriptionEquals(String title, Pageable pageable);

}
