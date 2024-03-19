package com.project.repository.helperRepository;

import com.project.entity.business.helperentity.Category_Property_Key;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface CategoryPropertyKeyRepository extends JpaRepository<Category_Property_Key, Long> {

    @Query("SELECT cpk FROM Category_Property_Key cpk WHERE cpk.category.id = :categoryId")
    List<Category_Property_Key> findByCategoryId(@Param("categoryId") Long categoryId);
}
