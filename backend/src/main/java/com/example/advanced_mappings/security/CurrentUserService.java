package com.example.advanced_mappings.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CurrentUserService {

    public String getCurrentUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName(); // This should be the email/username from JWT
    }

    public Long getCurrentUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getPrincipal() instanceof AuthUser authUser) {
            return authUser.getUser().getId(); // access ID via user
        }
        throw new IllegalStateException("User is not authenticated");
    }

    public boolean isCurrentUser(String emailFromRequest) {
        return getCurrentUsername().equals(emailFromRequest);
    }
}