package com.project.repository.business;

import com.project.entity.business.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressCountryRepository extends JpaRepository<Country, Long> {
}
