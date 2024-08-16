package by.bsuir.kostyademens.model.path;

import lombok.Getter;

@Getter
public class MinioPath {

    private final String path;

    public MinioPath(String userRootFolder, String path) {
        if (path == null || path.isEmpty() || path.equals("/")) {
            this.path = userRootFolder;
        } else {
            this.path = userRootFolder + path;
        }
    }
}
