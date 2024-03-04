package com.project.repository.business;

import com.project.entity.business.helperentity.Advert_Type;
import com.project.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdvertTypesRepository extends JpaRepository<Advert_Type, Long> {

    User findByEmail(String email);
}
