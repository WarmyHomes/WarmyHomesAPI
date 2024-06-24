package com.project.repository.business;

import com.project.entity.business.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FavoritesRepository extends JpaRepository<Favorite, Long> {

    @Query("SELECT f FROM Favorite f WHERE f.user.id = :userId")
    List<Favorite> findByUserId(@Param("userId") Long userId);

    @Query("SELECT f FROM Favorite f WHERE f.user.id = :userId AND f.advert.id = :advertId")
    Favorite findByUserIdAndAdvertId(@Param("userId") Long userId, @Param("advertId") Long advertId);

    @Modifying
    @Query("DELETE FROM Favorite f WHERE f.user.id = :userId")
    void deleteAllByUserId(@Param("userId") Long userId);

    @Query("SELECT f FROM Favorite f WHERE f.id = :favoriteId AND f.user.id = :userId")
    Favorite findByIdAndUserId(@Param("favoriteId") Long favoriteId, @Param("userId") Long userId);
}
