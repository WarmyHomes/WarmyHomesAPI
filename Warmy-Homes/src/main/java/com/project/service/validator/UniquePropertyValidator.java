package com.project.service.validator;

import com.project.entity.user.User;
import com.project.exception.ConflictException;
import com.project.payload.messages.ErrorMessages;
import com.project.payload.request.abstracts.AbstractUserRequest;
import com.project.payload.request.user.UpdateUserRequest;
import com.project.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UniquePropertyValidator {
    private final UserRepository userRepository;


    public void checkUniqueProperties(User user, AbstractUserRequest baseUserRequest){

        String updatedEmail ="";
        boolean isChanged = false;


        if(!user.getEmail().equalsIgnoreCase(baseUserRequest.getEmail())){
            updatedEmail = baseUserRequest.getEmail();
            isChanged = true;
        }

        if(isChanged){
            checkDuplicate(updatedEmail);
        }
    }

    public void checkUniquePropertiess(User user, UpdateUserRequest baseUserRequest){

        String updatedEmail ="";
        boolean isChanged = false;


        if(!user.getEmail().equalsIgnoreCase(baseUserRequest.getEmail())){
            updatedEmail = baseUserRequest.getEmail();
            isChanged = true;
        }

        if(isChanged){
            checkDuplicate(updatedEmail);
        }
    }

    public void checkDuplicate(String email){

        if(userRepository.existsByEmail(email)){
            throw new ConflictException(String.format(ErrorMessages.ALREADY_REGISTER_MESSAGE_EMAIL, email));
        }


    }
}
