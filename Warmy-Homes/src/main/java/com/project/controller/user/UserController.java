package com.project.controller.user;

import com.project.payload.request.user.LoginRequest;
import com.project.payload.request.user.UserRequest;
import com.project.payload.response.abstracts.BaseUserResponse;
import com.project.payload.response.business.ResponseMessage;
import com.project.payload.response.user.AuthResponse;
import com.project.payload.response.user.UserResponse;
import com.project.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    // F01 - login
    @PostMapping("/login") // http://localhost:8080/login
    @PreAuthorize("hasAnyAuthority('ANONYMOUS')")
    public ResponseEntity<AuthResponse> authenticateUser(@RequestBody @Valid LoginRequest loginRequest){

        return userService.authenticateUser(loginRequest);
    }

    //F02 - register
    @PostMapping("/register") // http://localhost:8080/register
    @PreAuthorize("hasAnyAuthority('ANONYMOUS')")
    public ResponseEntity<ResponseMessage<UserResponse>> saveUser(@PathVariable String userRole,
                                                                  @RequestBody @Valid UserRequest userRequest) {
        return ResponseEntity.ok(userService.saveUser(userRequest,userRole));
    }

    //F03

    //F04

    //F05

    //F06

    //F07

    //F08

    //F09


    ///F10 -  getUserById
    @GetMapping("/users/{userId}/admin") //http://localhost:8080/users/:id/admin
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    public ResponseMessage<BaseUserResponse> getUserById(@PathVariable Long id){
        return userService.getUserById(id);
    }

    //F11

    //F12

}
