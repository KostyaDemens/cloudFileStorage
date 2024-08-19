package by.bsuir.kostyademens.model.path;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.nio.file.Paths;

@Getter
@AllArgsConstructor
public class ItemPath {

    private String path;

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

    public String getPathWithoutUserFolder() {
        path = path.substring(path.indexOf("/") + 1);
        if (path.endsWith("/")) {
            String truncatedPath = StringUtils.chop(path);
            return path.substring(0, truncatedPath.lastIndexOf("/") + 1);
        } else {
            return path.substring(0, path.lastIndexOf("/") + 1);
        }
    }
}
