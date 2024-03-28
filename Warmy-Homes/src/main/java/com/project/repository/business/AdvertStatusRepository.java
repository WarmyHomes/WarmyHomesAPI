package com.project.repository.business;

import com.project.entity.business.helperentity.AdvertStatusRole;
import com.project.entity.enums.AdvertStatusType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AdvertStatusRepository extends JpaRepository<AdvertStatusRole,Long> {
    @Query("SELECT a FROM AdvertStatusRole a WHERE a.advertStatusType = ?1")
    Optional<AdvertStatusRole>findByEnumAdvertStatusEquals(AdvertStatusType statusType);
}
