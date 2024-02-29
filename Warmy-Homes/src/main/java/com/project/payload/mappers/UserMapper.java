package com.project.payload.mappers;

import com.project.entity.user.User;
import com.project.payload.request.abstracts.BaseUserRequest;
import com.project.payload.response.user.UserResponse;
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
                .role(user.getUserRole().getRoleType().name())
                .build();
    }

    public User mapUserRequestToUser(BaseUserRequest userRequest){

        return User.builder()

                .first_name(userRequest.getFirst_name())
                .last_name(userRequest.getLast_name())
                .password_hash(userRequest.getPassword_hash())
                .phone(userRequest.getPhone())
                .create_at(userRequest.getCreate_at())
                .update_at(userRequest.getUpdate_at())
                .reset_password_code(userRequest.getReset_password_code())
                .built_in(userRequest.getBuilt_in())
                .email(userRequest.getEmail())
                .build();

    }

}
