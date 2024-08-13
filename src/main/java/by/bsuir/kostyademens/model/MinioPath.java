package by.bsuir.kostyademens.model;

import by.bsuir.kostyademens.service.UserService;
import lombok.Getter;
import lombok.Setter;

import java.nio.file.Paths;


@Getter
@Setter
public class MinioPath {


    private String fullPath;

    private UserService userService = new UserService();

    public MinioPath(String fullPath) {
        this.fullPath = (fullPath != null) ? getUserFolder() + fullPath : getUserFolder();
    }

    public String getFileName(String itemName) {
        return Paths.get(itemName).getFileName().toString();
    }

    public String getPathWithoutUserFolder(String itemName) {
        return itemName.substring(getUserFolder().length());
    }

    private String getUserFolder() {
        return "user-" + userService.getUserFromContext().getId() + "-files/";
    }


}
