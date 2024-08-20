package by.bsuir.kostyademens.model;

import by.bsuir.kostyademens.model.path.ItemPath;
import lombok.Getter;

import java.util.*;

@Getter
public class BreadCrumb {

    private final LinkedHashMap<String, String> map = new LinkedHashMap<>();

    public LinkedHashMap<String, String> createBreadCrumbs(String path, String userRootFolder) {
        while (!path.isEmpty()) {
            ItemPath itemPath = new ItemPath(path);

            String key = itemPath.getItemPath(userRootFolder);
            String value = itemPath.getItemName();

            if (key.equals("/")) {
                map.put("/", "Home");
                return reverseMap(map);
            } else {
                map.put(key, value);
            }

            path = path.substring(0, path.length() - value.length() - 1);
        }
        return reverseMap(map);
    }

    private LinkedHashMap<String, String> reverseMap(LinkedHashMap<String, String> originalMap) {
        LinkedHashMap<String, String> reversed = new LinkedHashMap<>();
        List<Map.Entry<String, String>> entries = new ArrayList<>(originalMap.entrySet());

        Collections.reverse(entries);

        for (Map.Entry<String, String> entry : entries) {
            reversed.put(entry.getKey(), entry.getValue());
        }

        return reversed;
    }

}
