package by.bsuir.kostyademens.service;

import by.bsuir.kostyademens.model.User;
import by.bsuir.kostyademens.repository.UserRepository;
import by.bsuir.kostyademens.security.SecureUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SecureUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public SecureUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found!");
        } else {
            return new SecureUserDetails(user.get());
        }
    }
}
