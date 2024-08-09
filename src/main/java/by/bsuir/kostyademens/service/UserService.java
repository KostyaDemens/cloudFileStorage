package by.bsuir.kostyademens.service;

import by.bsuir.kostyademens.model.User;
import by.bsuir.kostyademens.model.security.SecureUserDetails;
import by.bsuir.kostyademens.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

//    private final UserRepository userRepository;

    public User getUserFromContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SecureUserDetails userDetails = (SecureUserDetails) authentication.getPrincipal();
        return userDetails.getUser();
    }
}
