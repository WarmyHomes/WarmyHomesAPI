package com.project.payload.mappers;

import com.project.entity.enums.RoleType;
import com.project.entity.user.User;
import com.project.payload.request.abstracts.BaseUserRequest;
import com.project.payload.request.user.UpdateUserRequest;
import com.project.payload.request.user.UserRequest;
import com.project.payload.response.user.UserAllFieldsResponse;
import com.project.payload.response.user.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserResponse mapUserToUserResponse(User user){
        return UserResponse.builder()
                .id(user.getId())
                .first_name(user.getFirst_name())
                .last_name(user.getLast_name())
                .phone(user.getPhone())
                .create_at(user.getCreate_at())
                .update_at(user.getUpdate_at())
                .email(user.getEmail())
                .role(user.getUserRole().getName())
                .build();
    }

    public UserAllFieldsResponse mapUserToUserAllFieldsResponse(User user){
     return    UserAllFieldsResponse.builder()
                .id(user.getId())
                .first_name(user.getFirst_name())
                .last_name(user.getLast_name())
                .phone(user.getPhone())
                .email(user.getEmail())
                .tour_requestList(user.getTour_requestList())
                .logs(user.getLogs())
                .advertList(user.getAdvertList())
                .favoriteList(user.getFavoriteList())
                .build();

    }

    public User mapUserRequestToUser(BaseUserRequest userRequest){

        return User.builder()

                .first_name(userRequest.getFirst_name())
                .last_name(userRequest.getLast_name())
                .password_hash(userRequest.getPassword_hash())
                .phone(userRequest.getPhone())
                .email(userRequest.getEmail())


                .build();

    }
    public User mapUserRequestToUpdatedUser(UserRequest userRequest, Long id){
        return User.builder()
                .id(id)
                .first_name(userRequest.getFirst_name())
                .last_name(userRequest.getLast_name())
                .password_hash(userRequest.getPassword_hash())
                .phone(userRequest.getPhone())
                .email(userRequest.getEmail())
                .userRole(userRequest.getUserRole())
                .built_in(userRequest.getBuilt_in())


                .build();
    }

    public User mapUserRequestToUpdatedUserr(UpdateUserRequest userRequest, Long id){
        return User.builder()
                .id(id)
                .first_name(userRequest.getFirst_name())
                .last_name(userRequest.getLast_name())

                .phone(userRequest.getPhone())
                .email(userRequest.getEmail())

                .build();
    }
    public Page<UserResponse> mapUserPageToUserResponsePage(Page<User> userPage) {
        return userPage.map(user -> UserResponse.builder()
                .id(user.getId())
                .first_name(user.getFirst_name())
                .last_name(user.getLast_name())
                .phone(user.getPhone())
                .create_at(user.getCreate_at())
                .update_at(user.getUpdate_at())
                .email(user.getEmail())
                .role(user.getUserRole().getName())
                .build());
    }

}
