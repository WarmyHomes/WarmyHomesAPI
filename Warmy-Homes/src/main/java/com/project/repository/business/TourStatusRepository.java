package com.project.repository.business;

import com.project.entity.business.helperentity.TourStatusRole;
import com.project.entity.enums.TourStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TourStatusRepository extends JpaRepository<TourStatusRole,Long> {
    @Query("SELECT tsr FROM TourStatusRole tsr WHERE tsr.tourStatus= ?1")
    Optional<TourStatusRole> findByEnumTourStatusEquals(TourStatus status);
}
