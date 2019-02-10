package com.practice.demo.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component
public final class SecurityUtils {

    public final Optional<String> getCurrentLoggedInUserName() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        if(Objects.nonNull(securityContext)) {
            Authentication authentication = securityContext.getAuthentication();
            if(Objects.nonNull(authentication)) {
                Object principal = securityContext.getAuthentication().getPrincipal();
                if (principal instanceof UserDetails) {
                    return Optional.ofNullable(((UserDetails) principal).getUsername());
                } else {
                    return Optional.ofNullable(principal.toString());
                }
            }
        }
        return Optional.empty();
    }

    public final Optional<UserDetails> getCurrentLoggedInUser() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        if(Objects.nonNull(securityContext)) {
            Authentication authentication = securityContext.getAuthentication();
            if(Objects.nonNull(authentication)) {
                Object principal = securityContext.getAuthentication().getPrincipal();
                if (principal instanceof UserDetails) {
                    return Optional.ofNullable((UserDetails) principal);
                }
            }
        }
        return Optional.empty();
    }
}
