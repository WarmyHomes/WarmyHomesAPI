package com.project.security.service;

import com.project.entity.user.User;
import com.project.repository.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user= userRepository.findByEmail(email);

        if (user != null){
            return new UserDetailsImpl(
                    user.getId(),
                    user.getEmail(),
                    user.getFirst_name(),
                    user.getLast_name(),
                    user.getUserRole().getName()
                    );
        }throw new UsernameNotFoundException("User : "+ email + "not found");

    }
}
