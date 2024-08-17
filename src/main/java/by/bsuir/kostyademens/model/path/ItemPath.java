package by.bsuir.kostyademens.model.path;

import org.apache.commons.io.file.PathUtils;

import java.nio.file.Paths;

public record ItemPath(String path) {

    public String getItemName() {
        return Paths.get(path).getFileName().toString();
    }

    public String getItemPath(String userRootFolder) {
        if (path.equals(userRootFolder)) {
            return "/";
        } else {
            return path.substring(userRootFolder.length());
        }
    }
}
