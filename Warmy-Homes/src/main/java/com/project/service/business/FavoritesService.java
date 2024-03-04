package com.project.service.business;

import com.project.entity.business.Advert;
import com.project.entity.business.Favorite;
import com.project.exception.ResourceNotFoundException;
import com.project.payload.mappers.AdvertMapper;
import com.project.payload.messages.ErrorMessages;
import com.project.payload.response.business.AdvertResponse;
import com.project.repository.business.FavoritesRepository;
import com.project.security.service.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FavoritesService {

    private final FavoritesRepository favoritesRepository;
    private final AdvertMapper advertMapper;



    //K01
    public List<AdvertResponse> getAuthenticatedUserFavorites() {

        Long authenticatedUserId = getAuthenticatedUserId();
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

    //? gereksiz olabilir
    private Long getAuthenticatedUserId() {
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
    }





}
