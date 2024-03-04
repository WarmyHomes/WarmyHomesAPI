package com.project.service.helper;

import com.project.entity.enums.RoleType;
import com.project.entity.user.User;
import com.project.exception.ResourceNotFoundException;
import com.project.payload.messages.ErrorMessages;
import com.project.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MethodHelper {

    private final UserRepository userRepository;




    // !!! isUserExistByUsername
    public User isUserExistByEmail(String email) {
        // !!! optional yapida calismayacaksak asagidaki gibi de kontrol edebiliriz
        User user = userRepository.findByEmailEquals(email);
        if (user.getId() == null) {
            throw new ResourceNotFoundException(ErrorMessages.NOT_FOUND_USER_MESSAGE);
        }
        return user;
    }

    // * isSlug


}
