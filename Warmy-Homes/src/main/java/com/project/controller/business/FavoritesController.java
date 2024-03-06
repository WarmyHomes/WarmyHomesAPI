package com.project.controller.business;

import com.project.entity.business.Advert;
import com.project.payload.response.business.AdvertResponse;
import com.project.service.business.FavoritesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/favorites")
@RequiredArgsConstructor
public class FavoritesController {

    private final FavoritesService favoritesService;

    //K01: It will get authenticated user`s favorites
    @GetMapping("/auth")
    @PreAuthorize("hasAnyAuthority('CUSTOMER')")
    public ResponseEntity<List<AdvertResponse>> getAuthenticatedUsersFavorites(){

        List<AdvertResponse> favorites = favoritesService.getAuthenticatedUserFavorites();
        return ResponseEntity.ok(favorites);

    }


    //K02: It will get user`s favorites
    @GetMapping("/favorites/admin/{id}")
    @PreAuthorize("hasAnyAuthority('MANAGER','ADMIN')")
    public ResponseEntity<List<AdvertResponse>> getUserFavorites(@PathVariable Long id) {
        List<AdvertResponse> userFavorites = favoritesService.getUserFavorites(id);
        return ResponseEntity.ok(userFavorites);
    }




    //K03: It will add/remove an advert to/from authenticated user`s favorites
    @PostMapping("/favorites/{advertId}/auth")
    @PreAuthorize("hasAnyAuthority('CUSTOMER')")
    public ResponseEntity<AdvertResponse> addOrRemoveAdvertFromFavorites(@PathVariable Long advertId){ //?requestbody

        AdvertResponse advert=favoritesService.addOrRemoveAdvertFromFavorites(advertId);
        return ResponseEntity.ok(advert);
    }




    //K04: It will remove all favorites of authenticated user


}
