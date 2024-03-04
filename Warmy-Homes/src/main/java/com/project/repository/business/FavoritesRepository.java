package com.project.repository.business;

import com.project.entity.business.Advert;
import com.project.entity.business.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoritesRepository extends JpaRepository<Favorite, Long> {
    List<Favorite> findByUserId(Long authenticatedUserId);

}
