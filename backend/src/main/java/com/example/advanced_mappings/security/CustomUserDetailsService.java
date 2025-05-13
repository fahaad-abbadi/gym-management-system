package com.example.advanced_mappings.security;

import com.example.advanced_mappings.exceptions.NotFoundException;
import com.example.advanced_mappings.models.User;
import com.example.advanced_mappings.repos.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmailIgnoreCase(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Email Not Found"));

        return AuthUser.builder()
                .user(user)
                .build();
    }
}

