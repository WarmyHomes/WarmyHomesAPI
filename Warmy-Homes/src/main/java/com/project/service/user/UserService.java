package com.project.service.user;

import com.project.payload.request.user.LoginRequest;
import com.project.payload.response.user.AuthResponse;
import com.project.repository.user.UserRepository;
import com.project.security.jwt.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;


    //login
    public static ResponseEntity<AuthResponse> authenticateUser(LoginRequest loginRequest) {
        String email= loginRequest.getEmail();
        String password= loginRequest.getPassword();

        Authentication authentication= authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email,password));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = "Bearer " + jwtUtils.generateJwtToken(authentication);





    }
}
