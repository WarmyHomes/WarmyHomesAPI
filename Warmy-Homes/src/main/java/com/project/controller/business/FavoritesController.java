package com.project.controller.business;

import com.project.payload.response.business.AdvertResponse;
import com.project.payload.response.business.ResponseMessage;
import com.project.security.service.UserDetailsImpl;
import com.project.service.business.FavoritesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/favorites")
@RequiredArgsConstructor
public class FavoritesController {

    private final FavoritesService favoritesService;

    //*** K01: It will get authenticated user's favorites
    @GetMapping("/auth")
    @PreAuthorize("hasAnyAuthority('CUSTOMER')")
    public ResponseEntity<List<AdvertResponse>> getAuthenticatedUsersFavorites() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Long id = favoritesService.getUserIdByEmail(email);
        List<AdvertResponse> favorites = favoritesService.getUserFavorites(id);
        return ResponseEntity.ok(favorites);
    }

    //*** K02: It will get user's favorites
    @GetMapping("/admin/{id}")
    @PreAuthorize("hasAnyAuthority('MANAGER', 'ADMIN')")
    public ResponseEntity<List<AdvertResponse>> getUserFavorites(@PathVariable Long id) {
        List<AdvertResponse> userFavorites = favoritesService.getUserFavorites(id);
        return ResponseEntity.ok(userFavorites);
    }

    //*** K03: It will add/remove an advert to/from authenticated user's favorites
    @PostMapping("/{advertId}/auth")
    @PreAuthorize("hasAnyAuthority('CUSTOMER')")
    public ResponseMessage<AdvertResponse> addOrRemoveAdvertFromFavorites(@PathVariable Long advertId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Long userId = favoritesService.getUserIdByEmail(email);
        return favoritesService.addOrRemoveAdvertFromFavorites(userId, advertId);
    }

    //K04
    @DeleteMapping("/auth")
    @PreAuthorize("hasAnyAuthority('CUSTOMER')")
    public ResponseEntity<String> deleteAllFavoritesOfAuthUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Long userId = userDetails.getId();

        String message = favoritesService.deleteAllFavorites(userId);
        return ResponseEntity.ok(message);
    }

    //*** K05: It will remove all favorites of a user
    @DeleteMapping("/admin/{userId}")
    @PreAuthorize("hasAnyAuthority('MANAGER', 'ADMIN')")
    public ResponseEntity<String> deleteAllFavoritesOfUser(@PathVariable Long userId) {
        return ResponseEntity.ok(favoritesService.deleteAllFavorites(userId));
    }

    //*** K06: It will remove a favorite of a user
    @DeleteMapping("/{userId}/{favoriteId}/admin")
    @PreAuthorize("hasAnyAuthority('MANAGER', 'ADMIN')")
    public ResponseEntity<String> deleteFavoriteOfUser(@PathVariable Long userId, @PathVariable Long favoriteId) {
        return ResponseEntity.ok(favoritesService.deleteFavorite(userId, favoriteId));
    }
}
