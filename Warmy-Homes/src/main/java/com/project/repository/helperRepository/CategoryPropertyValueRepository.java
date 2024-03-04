package com.project.repository.helperRepository;


import com.project.entity.business.helperentity.Category_Property_Key;
import com.project.entity.business.helperentity.Category_Property_Value;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryPropertyValueRepository extends JpaRepository<Category_Property_Value, Long> {
    // CategoryPropertyKey'e bağlı tüm CategoryPropertyValue kayıtlarını siler
    void deleteByCategoryPropertyKey(Category_Property_Key categoryPropertyKey);
}