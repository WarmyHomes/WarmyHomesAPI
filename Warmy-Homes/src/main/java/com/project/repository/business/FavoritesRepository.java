package com.project.repository.business;


import com.project.entity.business.Favorite;
import com.project.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FavoritesRepository extends JpaRepository<Favorite, Long> {


    List<Favorite> findByUserId(Long authenticatedUserId);

    Favorite findByUserIdAndAdvertId(Long userId, Long advertId);

    void deleteAllByUserId(Long userId);

    Favorite findByIdAndUserId(Long favoriteId, Long userId);

}
