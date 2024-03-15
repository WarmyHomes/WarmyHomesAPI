package com.project.repository.business;

import com.project.entity.business.helperentity.Advert_Type;
import com.project.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AdvertTypesRepository extends JpaRepository<Advert_Type, Long> {

   // User findByEmail(String email);

    // NOT: This method wrote for Report.
    @Query("SELECT COUNT (DISTINCT a.title) FROM Advert_Type a ")
    Long countAllAdvertType();
}
