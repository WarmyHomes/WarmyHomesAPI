package com.project.controller.user;

import com.project.payload.messages.SuccessMessages;
import com.project.payload.request.abstracts.AbstractUserRequest;
import com.project.payload.request.abstracts.BaseUserRequest;
import com.project.payload.request.user.*;
import com.project.payload.response.abstracts.BaseUserResponse;
import com.project.payload.response.business.ResponseMessage;
import com.project.payload.response.user.AuthResponse;
import com.project.payload.response.user.UserResponse;
import com.project.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;




    // F01 - login
    @PostMapping("/login") // http://localhost:8080/login
   // @PreAuthorize("hasAnyAuthority('ANONYMOUS')")
    //todo burayi sildik
    public ResponseEntity<AuthResponse> authenticateUser(@RequestBody @Valid LoginRequest loginRequest){

        return userService.authenticateUser(loginRequest);
    }

    //F02 - register
    @PostMapping("/register") // http://localhost:8080/register
   // @PreAuthorize("hasAnyAuthority('ANONYMOUS')")
    public ResponseEntity<ResponseMessage<UserResponse>> saveUser( @RequestBody @Valid UserRequest userRequest) {
        return ResponseEntity.ok(userService.saveUser(userRequest));
    }




   //F04 It will update password
   @PostMapping("/reset-password") //http://localhost:8080/reset-password
   public String updatePassword(@RequestBody UserUpdatePasswordRequest request,
                               HttpServletRequest servletRequest){

      userService.updatePassword(request,servletRequest);
      return "Password updated!";

   }





    //F05 /users/auth http://localhost:8080/users/auth
    @GetMapping("/users/auth")
    @PreAuthorize("hasAnyAuthority('CUSTOMER','MANAGER','ADMIN')")
    public ResponseEntity<UserResponse> getUser(HttpServletRequest request){

        String email = (String) request.getAttribute("email");
        UserResponse userResponse= userService.getUser(email);
        return ResponseEntity.ok(userResponse);
    }


    //F06/users/auth It will update the authenticated user
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','CUSTOMER')")
    @PutMapping("/users/auth")// http://localhost:8080/users/auth
    public ResponseMessage<UserResponse> updateUser(@RequestBody @Valid AbstractUserRequest userRequest,
                                                                  HttpServletRequest servletRequest) {
        return userService.updateUser(userRequest, servletRequest);
    }

    //F07 It will update the authenticated user’s password
    @PostMapping("/users/auth") // http://localhost:8080/users/auth
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','CUSTOMER')")
    public ResponseMessage<String> updateUserPassword(HttpServletRequest request,
                                                     @RequestBody @Valid PasswordUpdateRequest baseUserRequest){

        return   userService.updateUserPassword(request,baseUserRequest);



    }



    //F08 /users/auth It will delete authenticated user
    @DeleteMapping("/users/auth")
    @PreAuthorize("hasAnyAuthority('CUSTOMER')")
    public ResponseEntity<String> deleteUser(HttpServletRequest request) {
        return ResponseEntity.ok(userService.deleteUser(request));
    }

    //F09 /users/admin  It will return users
    @GetMapping("/users/admin")
    @PreAuthorize("hasAnyAuthority('MANAGER','ADMIN')")
    public ResponseEntity<Page<UserResponse>> getUserByPage(
            @RequestParam (defaultValue = "") String q,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "create_at") String sort,
            @RequestParam(value = "type", defaultValue = "desc") String type
    ) {
        Page<UserResponse> getUserByPage = userService.getUserByPage(q,page,size,sort,type);
        return new ResponseEntity<>(getUserByPage, HttpStatus.OK);
    }


    ///F10 -  It will return a user
    @GetMapping("/users/{id}/admin") //http://localhost:8080/users/:id/admin
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    public ResponseMessage<BaseUserResponse> getUserById(@PathVariable Long id){
        return userService.getUserById(id);
    }

    //F11 /users/4/admin It will update the user
    @PutMapping("/users/{id}/admin")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    public ResponseMessage<BaseUserResponse> updateUserById(@RequestBody @Valid UpdateUserRequest userRequest,
                                                            @PathVariable Long id) {
        return userService.updateUserById(userRequest, id);
    }

    //F12 /users/4/admin It will delete the user
    @DeleteMapping("/users/{id}/admin")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    public ResponseMessage<BaseUserResponse> deleteUser(@PathVariable Long id,HttpServletRequest servletRequest) {
        return userService.deleteUserById(id,servletRequest);
    }

}
