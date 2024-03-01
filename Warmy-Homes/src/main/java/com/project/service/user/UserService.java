package com.project.service.user;

import com.project.entity.enums.RoleType;
import com.project.entity.user.User;
import com.project.exception.ResourceNotFoundException;
import com.project.payload.mappers.UserMapper;
import com.project.payload.messages.ErrorMessages;
import com.project.payload.messages.SuccessMessages;
import com.project.payload.request.user.LoginRequest;
import com.project.payload.request.user.UserRequest;
import com.project.payload.response.abstracts.BaseUserResponse;
import com.project.payload.response.business.ResponseMessage;
import com.project.payload.response.user.AuthResponse;
import com.project.payload.response.user.UserResponse;
import com.project.repository.user.UserRepository;
import com.project.security.jwt.JwtUtils;
import com.project.security.service.UserDetailsImpl;
import com.project.service.validator.UniquePropertyValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;
    private final UniquePropertyValidator uniquePropertyValidator;
    private final UserMapper userMapper;
    private final UserRoleService userRoleService;
    private final PasswordEncoder passwordEncoder;




    // F01 - login
    public ResponseEntity<AuthResponse> authenticateUser(LoginRequest loginRequest) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email,password));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = "Bearer " + jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        Set<String> roles = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        Optional<String> role = roles.stream().findFirst();

        AuthResponse.AuthResponseBuilder authResponse = AuthResponse.builder();
        authResponse.email(userDetails.getEmail());
        authResponse.first_name(userDetails.getFirstName());
        authResponse.last_name(userDetails.getLast_name());
        authResponse.token(token.substring(7));

        role.ifPresent(authResponse::role);
        return ResponseEntity.ok(authResponse.build());


    }

    //F02 - register
    public ResponseMessage<UserResponse> saveUser(UserRequest userRequest, String userRole) {
        uniquePropertyValidator.checkDuplicate(userRequest.getEmail());
        User user = userMapper.mapUserRequestToUser(userRequest);

        if(userRole.equalsIgnoreCase(RoleType.ADMIN.name())){

            if(Objects.equals(userRequest.getEmail(),"Admin")){
                user.setBuilt_in(true);
            }
            user.setUserRole(userRoleService.getUserRole(RoleType.ADMIN));
        } else if (userRole.equalsIgnoreCase("Manager")) {
            user.setUserRole(userRoleService.getUserRole(RoleType.MANAGER));
        } else if (userRole.equalsIgnoreCase("Customer")) {
            user.setUserRole(userRoleService.getUserRole(RoleType.CUSTOMER));
        } else if (userRole.equalsIgnoreCase("Anonymous")) {
            user.setUserRole(userRoleService.getUserRole(RoleType.ANONYMOUS));
        } else {
            throw new ResourceNotFoundException(String.format(ErrorMessages.NOT_FOUND_USER_USERROLE_MESSAGE, userRole));
        }

        user.setPassword_hash(passwordEncoder.encode(user.getPassword_hash()));

        User savedUser = userRepository.save(user);


        return ResponseMessage.<UserResponse>builder()
                .message(SuccessMessages.USER_CREATED)
                .object(userMapper.mapUserToUserResponse(savedUser))
                .build();

    }


    ///F10 -  getUserById
    public ResponseMessage<BaseUserResponse> getUserById(Long id) {
        BaseUserResponse baseUserResponse = null;

        User user = userRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException(String.format(ErrorMessages.NOT_FOUND_USER_MESSAGE, id)));

        baseUserResponse = userMapper.mapUserToUserResponse(user);
        return ResponseMessage.<BaseUserResponse>builder()
                .message(SuccessMessages.USER_FOUND)
                .httpStatus(HttpStatus.OK)
                .object(baseUserResponse)
                .build();

    }


}
