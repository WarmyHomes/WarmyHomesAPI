package com.project.repository.business;

import com.project.entity.business.District;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressDistrictRepository extends JpaRepository<District, Long> {
}
