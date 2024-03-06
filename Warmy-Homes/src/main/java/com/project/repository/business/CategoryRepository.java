package com.project.repository.business;

import com.project.entity.business.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

   // Page<Category> findByTitleContainingAndIsctiveTrue(String title, Pageable pageable);

    Page<Category> findByIsActiveTrue(Pageable pageable);

    Page<Category> findByTitleContainingAndIsActiveTrue(String query, Pageable pageable);

    Page<Category> findByTitleContaining(String query, Pageable pageable);

    @Query("SELECT title, COUNT(title)= ?1 FROM City GROUP BY title = ?2")
    List<Object[]> countCategories();
}
