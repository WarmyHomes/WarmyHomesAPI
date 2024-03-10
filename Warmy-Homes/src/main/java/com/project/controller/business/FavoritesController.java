package com.project.controller.business;

import com.project.payload.response.business.AdvertResponse;
import com.project.payload.response.business.ResponseMessage;
import com.project.service.business.FavoritesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/favorites")
@RequiredArgsConstructor
public class FavoritesController {

    private final FavoritesService favoritesService;


    //*** K01: It will get authenticated user`s favorites
    @GetMapping("/auth")
    @PreAuthorize("hasAnyAuthority('CUSTOMER')")
    public ResponseEntity<List<AdvertResponse>> getAuthenticatedUsersFavorites(HttpServletRequest request){

        Long id = (Long) request.getAttribute("id");
        List<AdvertResponse> favorites = favoritesService.getUserFavorites(id);
        return ResponseEntity.ok(favorites);

    }


    //*** K02: It will get user`s favorites
    @GetMapping("/admin/{id}")
    @PreAuthorize("hasAnyAuthority('MANAGER','ADMIN')")
    public ResponseEntity<List<AdvertResponse>> getUserFavorites(@PathVariable Long id) {
        List<AdvertResponse> userFavorites = favoritesService.getUserFavorites(id);
        return ResponseEntity.ok(userFavorites);
    }




    //*** K03: It will add/remove an advert to/from authenticated user`s favorites
    @PostMapping("/{advertId}/auth")
    @PreAuthorize("hasAnyAuthority('CUSTOMER')")
    public ResponseMessage<AdvertResponse> addOrRemoveAdvertFromFavorites(@PathVariable Long advertId,
                                                                          HttpServletRequest request){ //?requestbody

        Long userId = (Long) request.getAttribute("id");

        return favoritesService.addOrRemoveAdvertFromFavorites(userId,advertId);
    }





    //*** K04: It will remove all favorites of authenticated user
    @DeleteMapping("/auth")
    @PreAuthorize("hasAnyAuthority('CUSTOMER')")
    public ResponseEntity<String> deleteAllFavoritesOfAuthUser(HttpServletRequest servletRequest){

        Long userId = (Long) servletRequest.getAttribute("id");
        return ResponseEntity.ok(favoritesService.deleteAllFavorites(userId));
    }





    //*** K05: It will remove all favorites of a user
    @DeleteMapping("/admin/{userId}")
    @PreAuthorize("hasAnyAuthority('MANAGER','ADMIN')")
    public ResponseEntity<String> deleteAllFavoritesOfUser(@PathVariable Long userId) {

        return ResponseEntity.ok(favoritesService.deleteAllFavorites(userId));
    }




    //*** K06: It will remove a favorite of a user
    @DeleteMapping("/{userId}/{favoriteId}/admin") //hem userId hem de favoriteId ye ihtiyacım olur bence
    @PreAuthorize("hasAnyAuthority('MANAGER','ADMIN')")
    public ResponseEntity<String> deleteFavoriteOfUser(@PathVariable Long userId, @PathVariable Long favoriteId){

        return ResponseEntity.ok(favoritesService.deleteFavorite(userId,favoriteId));

    }


}
