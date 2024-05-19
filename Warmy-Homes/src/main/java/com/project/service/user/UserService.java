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
import com.project.payload.request.user.*;
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
    //private final MailService mailService;
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

        Set<UserRole> userRole = new HashSet<>();

        UserRole customer = userRoleService.getUserRole(RoleType.CUSTOMER);

        userRole.add(customer);
        User user = userMapper.mapUserRequestToUser(userRequest);

        user.setBuilt_in(Boolean.FALSE);

        user.setCreate_at(LocalDateTime.now());
        user.setUserRole(customer) ;



        user.setPassword_hash(passwordEncoder.encode(user.getPassword_hash()));


        User savedUser = userRepository.save(user);


        return ResponseMessage.<UserResponse>builder()
                .message(SuccessMessages.USER_CREATED)
                .object(userMapper.mapUserToUserResponse(savedUser))
                .build();

    }





    //F04 It will update password
    public void updatePassword(UserUpdatePasswordRequest request, HttpServletRequest servletRequest) {
        String reset_code= (String) servletRequest.getAttribute("reset_password_code");
        String code= request.getReset_password_codee();
        if (code != null && !code.equals(reset_code)){
            throw new BadRequestException(ErrorMessages.NOT_VALID_CODE);
        }
        if (!(request.getPassword_hash().equals(request.getRetry_password_hash()))){
            throw new BadRequestException(ErrorMessages.PASSWORD_NOT_MATCHED);
        }
        String email= (String) servletRequest.getAttribute("email");
        User user= userRepository.findByEmail(email);
        String newPassword= passwordEncoder.encode(request.getPassword_hash());
        user.setPassword_hash(newPassword);
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
        user.setUpdate_at(LocalDateTime.now());
        userRepository.save(user);

        String message = SuccessMessages.USER_UPDATE_MESSAGE;
       return ResponseMessage.<UserResponse>builder()
                .message(SuccessMessages.USER_UPDATE_MESSAGE)
                .object(userMapper.mapUserToUserResponse(user))
                .build();

    }

    //F07 It will update the authenticated user’s password
    public ResponseMessage<String> updateUserPassword(HttpServletRequest request, PasswordUpdateRequest baseUserRequest) {
        String email= (String) request.getAttribute("email");
        User user = userRepository.findByEmail(email);
        if (Boolean.TRUE.equals(user.getBuilt_in())){
            throw  new BadRequestException(ErrorMessages.NOT_PERMITTED_METHOD_MESSAGE);
        }
//        if(!passwordEncoder.matches(baseUserRequest.getPassword_hash(), user.getPassword_hash())){
//            throw  new BadRequestException(ErrorMessages.PASSWORD_NOT_MATCHED);
//        }

        if (!(user.getPassword_hash().equalsIgnoreCase(baseUserRequest.getCurrent_password()))){
            throw new BadRequestException(ErrorMessages.PASSWORD_NOT_MATCHED);
        }

        if (!(baseUserRequest.getNew_password().equalsIgnoreCase(baseUserRequest.getRetry_new_password()))){
            throw new BadRequestException(ErrorMessages.PASSWORDS_NOT_MATCHED);
        }

        String encodedPassword = passwordEncoder.encode(baseUserRequest.getNew_password());
        user.setPassword_hash(encodedPassword);
        userRepository.save(user);

        String response = SuccessMessages.PASSWORD_CHANGED_RESPONSE_MESSAGE;
        return ResponseMessage.<String>builder()
                .message(response)
                .httpStatus(HttpStatus.OK)
                .build();

    }


    //F08 /users/auth It will delete authenticated user
    public String deleteUser(HttpServletRequest servletRequest) {
        //biult_in control
        Boolean isBuiltlIn = (Boolean) servletRequest.getAttribute("built_in");
        if (Boolean.TRUE.equals(isBuiltlIn)){
            throw new BadRequestException(ErrorMessages.NOT_PERMITTED_METHOD_MESSAGE);
        }

        String email = (String) servletRequest.getAttribute("email");
        User user=userRepository.findByEmail(email);
        Long id= user.getId();

        //isadvert and tour request


        if (!( user.getTour_requestList().isEmpty()) || !(user.getAdvertList().isEmpty())){
            throw new BadRequestException(ErrorMessages.USER_CAN_NOT_DELETED);
        }

        userRepository.deleteById(id);
        return SuccessMessages.USER_DELETE;


    }

    //F09 /users/admin  It will return users
    public Page<UserResponse> getUserByPage(String q,int page, int size, String sort, String type) {
        if (q == null && q.isEmpty()) {
            new ResourceNotFoundException("dddddddddd");
        }
        Pageable pageable = pageableHelper.getPageableWithProperties(page, size, sort, type);
        Page<User> usersPage = userRepository.findByUser(pageable, q);
        return userMapper.mapUserPageToUserResponsePage(usersPage);
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
    public ResponseMessage<BaseUserResponse> updateUserById(UpdateUserRequest userRequest, Long id) {
        User user= isUserExist(id);
        Boolean isBuiltlIn= user.getBuilt_in();

        //userRequest.setUserRole(user.getUserRole());
        if ( Boolean.TRUE.equals(isBuiltlIn)){
            throw new BadRequestException(ErrorMessages.NOT_PERMITTED_METHOD_MESSAGE);
        }
        uniquePropertyValidator.checkUniquePropertiess(user,userRequest);
        User updatedUser = userMapper.mapUserRequestToUpdatedUserr(userRequest, id);


        updatedUser.setReset_password_code(passwordEncoder.encode(user.getReset_password_code()));
        updatedUser.setPassword_hash(passwordEncoder.encode(user.getPassword_hash()));
        updatedUser.setUserRole(user.getUserRole());
        updatedUser.setBuilt_in(false);
        updatedUser.setCreate_at(user.getCreate_at());
        updatedUser.setUpdate_at(LocalDateTime.now());

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

        user.setUserRole(admin) ;
        userRepository.save(user);

        return SuccessMessages.USER_CREATED;
    }

    public String saveManager(UserRequest userRequest) {

        Set<UserRole> userRole = new HashSet<>();

        UserRole manager = userRoleService.getUserRole(RoleType.MANAGER);

        userRole.add(manager);

        User user = userMapper.mapUserRequestToUser(userRequest);

        user.setBuilt_in(Boolean.TRUE);


        user.setPassword_hash(passwordEncoder.encode(userRequest.getPassword_hash()));

        user.setCreate_at(LocalDateTime.now());

        user.setUserRole(manager) ;
        userRepository.save(user);

        return SuccessMessages.USER_CREATED;
    }

  public long countAllAdmins(){
      return userRepository.countAdmin(RoleType.ADMIN);
  }

    public long countAllManagers(){
        return userRepository.countManager(RoleType.MANAGER);
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
