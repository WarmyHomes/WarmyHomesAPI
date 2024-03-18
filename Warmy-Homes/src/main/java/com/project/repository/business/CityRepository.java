package com.project.repository.business;

import com.project.entity.business.City;
import com.project.payload.response.business.helperresponse.CityForAdvertResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Objects;

public interface CityRepository extends JpaRepository<City, Long> {

    @Query("SELECT c.name, COUNT(c) FROM City c GROUP BY c.name")
    List<Object[]> countCities();


}
