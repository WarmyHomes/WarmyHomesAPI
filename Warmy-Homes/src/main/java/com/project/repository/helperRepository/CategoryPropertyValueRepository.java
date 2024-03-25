package com.project.repository.helperRepository;


import com.project.entity.business.helperentity.Category_Property_Key;
import com.project.entity.business.helperentity.Category_Property_Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CategoryPropertyValueRepository extends JpaRepository<Category_Property_Value, Long> {
    // CategoryPropertyKey'e bağlı tüm CategoryPropertyValue kayıtlarını siler
    @Modifying
    @Query("delete from Category_Property_Value cpv where cpv.category_property_key = :categoryPropertyKey")
    void deleteByCategoryPropertyKey(@Param("categoryPropertyKey") Category_Property_Key categoryPropertyKey);

}