package com.project.repository.business;

import com.project.entity.business.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressCityRepository extends JpaRepository<City, Long> {
}
