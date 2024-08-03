package by.bsuir.kostyademens.util;

import by.bsuir.kostyademens.model.User;
import by.bsuir.kostyademens.repository.UserRepository;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {

    private final UserRepository userRepository;

    public UserValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            errors.rejectValue("username", "", "User with such username already exists!");
        }

    }
}
