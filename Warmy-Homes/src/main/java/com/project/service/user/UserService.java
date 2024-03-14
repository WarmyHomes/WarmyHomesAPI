package com.project.service.user;

import com.project.entity.business.Advert;
import com.project.entity.business.Tour_Request;
import com.project.entity.enums.RoleType;
import com.project.entity.user.User;
import com.project.entity.user.UserRole;
import com.project.exception.BadRequestException;
import com.project.exception.ResourceNotFoundException;
import com.project.payload.mappers.UserMapper;
import com.project.payload.messages.ErrorMessages;
import com.project.payload.messages.SuccessMessages;
import com.project.payload.request.abstracts.AbstractUserRequest;
import com.project.payload.request.abstracts.BaseUserRequest;
import com.project.payload.request.user.LoginRequest;
import com.project.payload.request.user.UserRequest;
import com.project.payload.request.user.UserUpdatePasswordRequest;
import com.project.payload.response.abstracts.BaseUserResponse;
import com.project.payload.response.business.ResponseMessage;
import com.project.payload.response.user.AuthResponse;
import com.project.payload.response.user.UserAllFieldsResponse;
import com.project.payload.response.user.UserResponse;
import com.project.repository.user.UserRepository;
import com.project.security.jwt.JwtUtils;
import com.project.security.service.UserDetailsImpl;
import com.project.service.helper.PageableHelper;
import com.project.service.mail.MailService;
import com.project.service.validator.UniquePropertyValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.*;
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
    private final MailService mailService;
    private final PageableHelper pageableHelper;




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
    public ResponseMessage<UserResponse> saveUser(UserRequest userRequest) {
        uniquePropertyValidator.checkDuplicate(userRequest.getEmail());
        User user = userMapper.mapUserRequestToUser(userRequest);
        UserRole userRole= new UserRole();
        userRole.setRoleType(RoleType.CUSTOMER);


        user.setPassword_hash(passwordEncoder.encode(user.getPassword_hash()));

        User savedUser = userRepository.save(user);


        return ResponseMessage.<UserResponse>builder()
                .message(SuccessMessages.USER_CREATED)
                .object(userMapper.mapUserToUserResponse(savedUser))
                .build();

    }


    //F03 /forgot-password
    public void sendResetPasswordCode(HttpServletRequest servletRequest) {
        String email= (String) servletRequest.getAttribute("email");
        String reset_password_code= (String) servletRequest.getAttribute("reset_password_code");
        mailService.sendMail(email,reset_password_code);
    }

    //F04 It will update password
    public void updatePassword(UserUpdatePasswordRequest request, HttpServletRequest servletRequest) {

         String code= request.getReset_password_code();
         String reset_code= (String) servletRequest.getAttribute("reset_password_code");
         if (!code.equals(reset_code)){
             throw new BadRequestException(ErrorMessages.NOT_VALID_CODE);
         }

         User user= (User) servletRequest.getAttribute("email");
         String new_password= passwordEncoder.encode(code);
         user.setPassword_hash(new_password);
         userRepository.save(user);

    }


    //F05 /users/auth http://localhost:8080/users/auth
    public UserResponse getUser(String email) {
        User user= userRepository.findByEmail(email);
        return userMapper.mapUserToUserResponse(user);
    }

    //F06/users/auth It will update the authenticated user
    public ResponseMessage<UserResponse> updateUser(AbstractUserRequest userRequest, HttpServletRequest servletRequest) {

        String email = (String) servletRequest.getAttribute("email");
        User user = userRepository.findByEmail(email);

        uniquePropertyValidator.checkUniqueProperties(user, userRequest );

        if (Boolean.TRUE.equals(user.getBuilt_in())){
            throw  new BadRequestException(ErrorMessages.NOT_PERMITTED_METHOD_MESSAGE);
        }

        user.setFirst_name(userRequest.getFirst_name());
        user.setLast_name(userRequest.getLast_name());
        user.setPhone(userRequest.getPhone());
        userRepository.save(user);

        String message = SuccessMessages.USER_UPDATE_MESSAGE;
       return ResponseMessage.<UserResponse>builder()
                .message(SuccessMessages.USER_UPDATE_MESSAGE)
                .object(userMapper.mapUserToUserResponse(user))
                .build();

    }

    //F07 It will update the authenticated user’s password
    public void updateUserPassword(HttpServletRequest request, BaseUserRequest baseUserRequest) {
        String email= (String) request.getAttribute("email");
        User user = userRepository.findByEmail(email);
        if (Boolean.TRUE.equals(user.getBuilt_in())){
            throw  new BadRequestException(ErrorMessages.NOT_PERMITTED_METHOD_MESSAGE);
        }
        if(!passwordEncoder.matches(baseUserRequest.getPassword_hash(), user.getPassword_hash())){
            throw  new BadRequestException(ErrorMessages.PASSWORD_NOT_MATCHED);
        }

        String encodedPassword = passwordEncoder.encode(baseUserRequest.getPassword_hash());
        user.setPassword_hash(encodedPassword);
        userRepository.save(user);

    }

    //F08 /users/auth It will delete authenticated user
    public String deleteUser(HttpServletRequest servletRequest, BaseUserRequest baseUserRequest) {
        //biult_in control
        Boolean isBuiltlIn = (Boolean) servletRequest.getAttribute("built_in");
        if (Boolean.TRUE.equals(isBuiltlIn)){
            throw new BadRequestException(ErrorMessages.NOT_PERMITTED_METHOD_MESSAGE);
        }

        User user = (User) servletRequest.getAttribute("password_hash");
        Long id= user.getId();

        //isadvert and tour request


        if (!( user.getTour_requestList().isEmpty()) || !(user.getAdvertList().isEmpty())){
            throw new BadRequestException(ErrorMessages.USER_CAN_NOT_DELETED);
        }

        userRepository.deleteById(id);
        return SuccessMessages.USER_DELETE;


    }

    //F09 /users/admin  It will return users
    public Page<UserResponse> getUserByPage(int page, int size, String sort, String type) {
        Pageable pageable = pageableHelper.getPageableWithProperties(page,size,sort,type);
        return userRepository.findAll(pageable).map(userMapper::mapUserToUserResponse);
    }

    ///F10 -  It will return a user
    public ResponseMessage<BaseUserResponse> getUserById(Long id) {
        UserAllFieldsResponse response=null;

        User user = userRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException(String.format(ErrorMessages.NOT_FOUND_USER_MESSAGE, id)));

      response= userMapper.mapUserToUserAllFieldsResponse(user);



        return ResponseMessage.<BaseUserResponse>builder()
                .message(SuccessMessages.USER_FOUND)
                .httpStatus(HttpStatus.OK)
                .object(response)
                .build();

    }

    //F11 /users/4/admin It will update the user
    public ResponseMessage<BaseUserResponse> updateUserById(UserRequest userRequest, Long id) {
        User user= isUserExist(id);
        if (user.getBuilt_in().equals(true)){
            throw new BadRequestException(ErrorMessages.NOT_PERMITTED_METHOD_MESSAGE);
        }
        uniquePropertyValidator.checkUniqueProperties(user,userRequest);
        User updatedUser = userMapper.mapUserRequestToUpdatedUser(userRequest, id);

        User savedUser = userRepository.save(updatedUser);

        return ResponseMessage.<BaseUserResponse>builder()
                .message(SuccessMessages.USER_UPDATE_MESSAGE)
                .httpStatus(HttpStatus.OK)
                .object(userMapper.mapUserToUserResponse(savedUser))
                .build();

    }

    //F12 /users/4/admin It will delete the user
    public ResponseMessage<BaseUserResponse> deleteUserById(Long id, HttpServletRequest servletRequest) {
        User user = userRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException(String.format(ErrorMessages.NOT_FOUND_USER_MESSAGE, id)));
       Boolean isBuiltlIn= user.getBuilt_in();
        if (Boolean.TRUE.equals(isBuiltlIn)){
            throw new BadRequestException(ErrorMessages.NOT_PERMITTED_METHOD_MESSAGE);
        }



        if (!( user.getTour_requestList().isEmpty()) || !(user.getAdvertList().isEmpty())){
            throw new BadRequestException(ErrorMessages.USER_CAN_NOT_DELETED);
        }

        User authorized = (User) servletRequest.getAttribute("email");

        if (authorized.getUserRole().equals(RoleType.MANAGER)){
            if (!(user.getUserRole().equals(RoleType.CUSTOMER))){
                throw new BadRequestException(ErrorMessages.NOT_PERMITTED_METHOD_MESSAGE);
            }
        }
        userRepository.deleteById(id);
        return ResponseMessage.<BaseUserResponse>builder()
                .message(SuccessMessages.USER_DELETE)
                .httpStatus(HttpStatus.OK)
                .object(userMapper.mapUserToUserResponse(user))
                .build();



    }


    public String saveAdmin(UserRequest userRequest) {

        Set<UserRole> userRole = new HashSet<>();

        UserRole admin = userRoleService.getUserRole(RoleType.ADMIN);

        userRole.add(admin);

        User user = userMapper.mapUserRequestToUser(userRequest);

        user.setBuilt_in(Boolean.TRUE);


        user.setPassword_hash(passwordEncoder.encode(userRequest.getPassword_hash()));

        user.setCreate_at(LocalDateTime.now());

        userRepository.save(user);

        return SuccessMessages.USER_CREATED;
    }

    public long countAllAdmins(){
        return userRepository.countAdmin(RoleType.ADMIN);
    }

    public User isUserExist(Long id){
        return userRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException(String.format(ErrorMessages.NOT_FOUND_USER_MESSAGE, id)));
    }


    // NOT: This method wrote for Report.
    public Long countAllUser() {
        return userRepository.countAllUser();
    }


    public User findUserByEmail(String email){
        User user = userRepository.findByEmailEquals(email);
        if (user != null){
            return user;
        }else {
            throw new BadRequestException(ErrorMessages.NOT_FOUND_USER_MESSAGE);
        }
    }

    public Page<Tour_Request>getUsersTourRequestById(Long id, Pageable pageable){
        return userRepository.findTourRequestByUserId(id,pageable);
    }
}
