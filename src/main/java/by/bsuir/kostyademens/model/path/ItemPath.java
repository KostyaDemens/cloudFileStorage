package by.bsuir.kostyademens.model.path;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.nio.file.Paths;

public record ItemPath(String path) {

    public String getFileName() {
        return Paths.get(path).getFileName().toString();
    }

    public String getFilePath(String userRootFolder) {
        if (path.equals(userRootFolder)) {
            return "/";
        } else {
            return path.substring(userRootFolder.length());
        }
    }
}
