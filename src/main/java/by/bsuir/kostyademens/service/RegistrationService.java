package by.bsuir.kostyademens.service;

import by.bsuir.kostyademens.exception.UserAlreadyExistsException;
import by.bsuir.kostyademens.model.User;
import by.bsuir.kostyademens.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final SimpleStorageService storageService;


    @Transactional
    public void register(User user) {

        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new UserAlreadyExistsException("User with username " + user.getUsername() + " already exists");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        storageService.createUserFolder(user.getId());
    }
}
