package com.project.repository.business;

import com.project.entity.business.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {


    // ID'ye göre Category nesnesini getiren özel sorgu
    @Query("SELECT c FROM Category c WHERE c.id = :id")
    Optional<Category> findCategoryById(Long id);

    // Aktif olan kategorileri başlık içeriğine göre filtreleyip sayfalandırarak döndürür
    @Query("SELECT c FROM Category c WHERE c.title LIKE %:query% AND c.is_active = TRUE")
    Page<Category> findByTitleContainingAndIsActiveTrue(String query, Pageable pageable);

    // Sadece aktif olan kategorileri sayfalandırarak döndürür
    @Query("SELECT c FROM Category c WHERE c.is_active = TRUE")
    Page<Category> findByIsActiveTrue(Pageable pageable);

    // Başlık içeriğine göre tüm kategorileri sayfalandırarak döndürür (aktiflik durumundan bağımsız)
    @Query("SELECT c FROM Category c WHERE c.title LIKE %:query%")
    Page<Category> findByTitleContaining(String query, Pageable pageable);

    // Kategori başlıklarına göre gruplandırılmış ve her bir başlık için kategori sayısını döndüren özel sorgu
    @Query("SELECT c.title, COUNT(c) FROM Category c GROUP BY c.title")
    List<Object[]> countTitleGroupedByTitle();

    // Başlık ve slug'a göre kategori sorgulama
    @Query("SELECT c FROM Category c WHERE c.title = :title")
    Optional<Category> findByTitle(String title);


    @Query("SELECT c FROM Category c WHERE c.slug = ?1")
    Optional<Category> findBySlug(String slug);


    @Query("SELECT COUNT(c) FROM Category c")
    Long countAllCategory();


    @Query(value = "SELECT c.title, COUNT(c) FROM categories c GROUP BY c.title", nativeQuery = true)
    List<Object[]> countCategories();

}