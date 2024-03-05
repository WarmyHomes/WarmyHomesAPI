package com.project.controller.business;

import com.project.entity.business.Advert;
import com.project.payload.response.business.AdvertResponse;
import com.project.service.business.FavoritesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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





    //K03: It will add/remove an advert to/from authenticated user`s favorites


}
