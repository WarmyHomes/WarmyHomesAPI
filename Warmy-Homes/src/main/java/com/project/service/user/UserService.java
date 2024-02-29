package com.project.service.user;

import com.project.payload.request.user.LoginRequest;
import com.project.payload.response.user.AuthResponse;
import com.project.repository.user.UserRepository;
import com.project.security.jwt.JwtUtils;
import com.project.security.service.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;



    //login
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
        authResponse.first_name(userDetails.getFirstName());
        authResponse.last_name(userDetails.ge)
        authResponse.token(token.substring(7));







    }
}
