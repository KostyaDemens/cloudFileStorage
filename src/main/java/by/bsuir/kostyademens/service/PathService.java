package by.bsuir.kostyademens.service;

import by.bsuir.kostyademens.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PathService {

    private final UserService userService;

    public String parse(String path) {
        if (path != null) {
            return getDefaultPath() + path;
        } else {
            return getDefaultPath();
        }
    }

    public String cutUserFolder(String path) {
        return path.substring(getDefaultPath().length());
    }

    private String getDefaultPath() {
        return "user-" + userService.getUserFromContext().getId() + "-files/";
    }


}