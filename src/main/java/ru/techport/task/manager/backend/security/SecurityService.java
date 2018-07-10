package ru.techport.task.manager.backend.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {
    @Autowired
    private AuthenticationManager authenticationManager;


    public boolean autoLogin(String username, String password) {
        Authentication user = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken("user", "123"));

        if (user.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(user);
            return true;
        }

        return false;
    }
}
