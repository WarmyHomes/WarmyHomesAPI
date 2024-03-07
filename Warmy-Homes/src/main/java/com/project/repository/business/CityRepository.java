package com.project.repository.business;

import com.project.entity.business.City;
import com.project.payload.response.business.helperresponse.CityForAdvertResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Objects;

public interface CityRepository extends JpaRepository<City, Long> {

    @Query("SELECT name, COUNT(name)= ?1 FROM City GROUP BY name = ?2")
    List<Object[]> countCities();

}
