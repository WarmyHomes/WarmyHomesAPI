package com.project.service.business;

import com.project.contactmessage.exception.ResourceNotFoundException;
import com.project.entity.business.Advert;
import com.project.entity.business.Favorite;
import com.project.payload.mappers.AdvertMapper;
import com.project.payload.messages.ErrorMessages;
import com.project.payload.messages.SuccessMessages;
import com.project.payload.response.business.AdvertResponse;
import com.project.payload.response.business.ResponseMessage;
import com.project.repository.business.AdvertRepository;
import com.project.repository.business.FavoritesRepository;
import com.project.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FavoritesService {

    private final FavoritesRepository favoritesRepository;
    private final AdvertMapper advertMapper;
    private final AdvertRepository advertRepository;
    private final UserRepository userRepository;



    //*** K01, K02
    public List<AdvertResponse> getUserFavorites(Long userId) {

        // Retrieve favorites for the authenticated user
        List<Favorite> favorites = favoritesRepository.findByUserId(userId);
        if (favorites.isEmpty()) {
            throw new ResourceNotFoundException(String.format(ErrorMessages.NOT_FOUND_USER_FAVORITES, userId));
        }

        // Map Favorite entities to Advert entities
        List<Advert> adverts = favorites.stream()
                .map(Favorite::getAdvert) //method reference to call the getAdvert method on each Favorite object in the stream
                .collect(Collectors.toList());

        // Map Advert entities to AdvertResponse entities
        return adverts.stream()
                .map(advertMapper::mapAdvertToAdvertResponse)
                .collect(Collectors.toList());

    }



    //*** K03:
    public ResponseMessage<AdvertResponse> addOrRemoveAdvertFromFavorites(Long userId, Long advertId) {

        //advert var mı yok mu
        Advert advert = advertRepository.findById(advertId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.ADVERT_NOT_FOUND));

        //authentike userın o adverti var mı yok mu
        Favorite favorite = favoritesRepository.findByUserIdAndAdvertId(userId, advertId);

        if (favorite != null) {
            // If the advert is in favorites, delete it
            favoritesRepository.delete(favorite);
            return ResponseMessage.<AdvertResponse>builder()
                    .httpStatus(HttpStatus.OK)
                    .message(SuccessMessages.ADVERT_DELETED)
                    .object(advertMapper.mapAdvertToAdvertResponse(advert))
                    .build();
        } else {
            // If the advert is not in favorites, add it
            favorite = new Favorite();
            favorite.setUser(userRepository.findById(userId)
                    .orElseThrow(() -> new ResourceNotFoundException(String.format(ErrorMessages.NOT_FOUND_USER_MESSAGE,userId))));
            favorite.setAdvert(advert);
            favorite.setCreate_at(LocalDateTime.now());
            favoritesRepository.save(favorite);

            return ResponseMessage.<AdvertResponse>builder()
                    .httpStatus(HttpStatus.CREATED)
                    .message(SuccessMessages.ADVERT_SAVE)
                    .object(advertMapper.mapAdvertToAdvertResponse(advert))
                    .build();

        }

    }



    //*** K04, K05:
    public String deleteAllFavorites(Long userId) {

        List<Favorite> favorites = favoritesRepository.findByUserId(userId);
        if (favorites.isEmpty()) {
            throw new ResourceNotFoundException(String.format(ErrorMessages.NOT_FOUND_USER_FAVORITES, userId));
        }
        favoritesRepository.deleteAllByUserId(userId); //deleteAll(favorites)
        return SuccessMessages.FAVORITES_DELETED;
    }



    //*** K06
    public String deleteFavorite(Long userId, Long favoriteId) {

        Favorite favorite = favoritesRepository.findByIdAndUserId(favoriteId, userId);
        if (favorite == null) {
            throw new ResourceNotFoundException(String.format(ErrorMessages.NOT_FOUND_USER_FAVORITES, userId));
        }

        favoritesRepository.delete(favorite);

        return SuccessMessages.FAVORITE_DELETED;

    }

}
