package com.project.repository.business;


import com.project.entity.business.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FavoritesRepository extends JpaRepository<Favorite, Long> {


    List<Favorite> findByUserId(Long authenticatedUserId);

    Favorite findByUserAndAdvertId(Long userId, Long advertId);


    Favorite findByUserIdAndAdvertId(Long userId, Long advertId);

}
