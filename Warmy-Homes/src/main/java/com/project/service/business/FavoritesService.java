package com.project.service.business;

import com.project.entity.business.Advert;
import com.project.entity.business.Favorite;
import com.project.entity.user.User;
import com.project.exception.ResourceNotFoundException;
import com.project.payload.mappers.AdvertMapper;
import com.project.payload.messages.ErrorMessages;
import com.project.payload.response.business.AdvertResponse;
import com.project.repository.business.AdvertRepository;
import com.project.repository.business.FavoritesRepository;
import com.project.repository.user.UserRepository;
import com.project.repository.user.UserRoleRepository;
import com.project.security.service.UserDetailsImpl;
import com.project.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FavoritesService {

    private final FavoritesRepository favoritesRepository;
    private final AdvertMapper advertMapper;
    private final UserDetailsImpl userDetails;

    private final UserService userService;

    private final UserRepository userRepository;

    private final AdvertRepository advertRepository;



    //K01
    public List<AdvertResponse> getAuthenticatedUserFavorites() {

        //userDetails uzerinden authentike kullanıcının id'sini aldım???????
        Long authenticatedUserId = userDetails.getId();
        // Retrieve favorites for the authenticated user
        List<Favorite> favorites = favoritesRepository.findByUserId(authenticatedUserId);

        // Map Favorite entities to Advert entities
        List<Advert> adverts = favorites.stream()
                .map(Favorite::getAdvert) //method reference to call the getAdvert method on each Favorite object in the stream
                .collect(Collectors.toList());//?neden turuncu?

        // Map Advert entities to AdvertResponse entities
        return adverts.stream()
                .map(advertMapper::mapAdvertToAdvertResponse)
                .collect(Collectors.toList());


    }


    //? tekrar authentike diye kontrol etmedim
    /* private Long getAuthenticatedUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetailsImpl) {
                return ((UserDetailsImpl) principal).getId();
            } else {
                throw new ResourceNotFoundException(ErrorMessages.NOT_RETRIEVE_USER_ID);
            }
        } else {
            throw new ResourceNotFoundException(ErrorMessages.NOT_RETRIEVE_USER_ID);
        }
    }*/



    //K02:
    public List<AdvertResponse> getUserFavorites(Long id) {

        List<Favorite> favorites = favoritesRepository.findByUserId(id);
        List<Advert> adverts = favorites.stream().map(Favorite::getAdvert).collect(Collectors.toList());

        return adverts.stream()
                .map(advertMapper::mapAdvertToAdvertResponse)
                .collect(Collectors.toList());

    }



    //K03:
    public AdvertResponse addOrRemoveAdvertFromFavorites(Long advertId) {

       /* Long authenticatedUserId = userDetails.getId();
        Favorite favorite = favoritesRepository.findByUserIdAndAdvertId(authenticatedUserId,advertId);


        if (favorite != null) {
            // If the advert is in favorites, remove it
            favoritesRepository.delete(favorite);
        } else {
            // If the advert is not in favorites, add it
            favorite = new Favorite();
            favorite.setUserId(authenticatedUserId);
            favorite.setUser();
            favorite.setAdvertId(advertId);
            favorite.setAdvert();
            favorite.setCreate_at(LocalDateTime.now());
            favoritesRepository.save(favorite);
        }*/
        Long authenticatedUserId = userDetails.getId();
        User user = userRepository.findById(authenticatedUserId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Favorite favorite = favoritesRepository.findByUserAndAdvertId(user, advertId);

        if (favorite != null) {
            // If the advert is in favorites, remove it
            favoritesRepository.delete(favorite);
            // Optionally, you can return a response indicating that the advert is removed
            return null;//new AdvertResponse("Advert removed from favorites");
        } else {
            // If the advert is not in favorites, add it
            Advert advert = advertRepository.findById(advertId)
                    .orElseThrow(() -> new EntityNotFoundException("Advert not found"));

            favorite = Favorite.builder()
                    .user(user)
                    .advert(advert)
                    .create_at(LocalDateTime.now())
                    .build();
            favoritesRepository.save(favorite);
            // Optionally, you can return a response indicating that the advert is added
            return null;//new AdvertResponse("Advert added to favorites");
        }

    }




}
