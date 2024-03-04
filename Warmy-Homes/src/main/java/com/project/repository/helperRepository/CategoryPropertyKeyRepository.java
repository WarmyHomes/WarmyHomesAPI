package com.project.repository.helperRepository;

import com.project.entity.business.helperentity.Category_Property_Key;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryPropertyKeyRepository extends JpaRepository<Category_Property_Key, Long> {
    List<Category_Property_Key> findByCategoryId(Long categoryId);
}